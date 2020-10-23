package com.akeijser.igtrader.igexternalapi

import java.time.LocalDateTime

object RestSession{
    private var oAuthResponse: OauthResponse? = null

    fun getSession(loginClient: LoginClient): OauthResponse {
        var instance = oAuthResponse

        //if No session yet, get one
        if (oAuthResponse == null) {
            instance = loginClient.oAuthLogin()
            instance.oauthToken.setExpiresDateTime()
            oAuthResponse = instance
            return oAuthResponse as OauthResponse
        }

        //If token is not yet expired, use the current token
        if (instance?.oauthToken?.expiresDateTime!! > LocalDateTime.now() ) {
            return oAuthResponse as OauthResponse
        }

        //Get new token with refresh token
        instance.oauthToken = loginClient.refreshOAuthToken(instance.oauthToken.refresh_token)
        instance.oauthToken.setExpiresDateTime()
        oAuthResponse = instance
        return oAuthResponse as OauthResponse
    }
}




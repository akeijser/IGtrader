package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.configuration.ApplicationConfig
import com.akeijser.igtrader.utils.IGSessionExceptions
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDateTime

object RestSession{

    private val LOGGER: Logger = LoggerFactory.getLogger(RestSession::class.java)
    private lateinit var oAuthResponse: OauthResponse
    private lateinit var config: ApplicationConfig


    @Throws(IGSessionExceptions::class)
    fun getOAuthDetails(config: ApplicationConfig): OauthResponse {
        this.config = config

        //if No session yet, get new one
        if (!this::oAuthResponse.isInitialized) {
            try {
                oAuthResponse = oAuthLogin()
            } catch (e: IGSessionExceptions) {
                LOGGER.info("Failed to do OAuth Login. ${e.message}")
                throw e
            }
            oAuthResponse.oauthToken.setExpiresDateTime()
            return oAuthResponse
        }

        //If token is not yet expired, use the current token
        if (oAuthResponse.oauthToken.expiresDateTime > LocalDateTime.now() ) {
            return oAuthResponse
        }

        //Get new token with refresh token
        try {
            oAuthResponse.oauthToken = refreshOAuthToken(oAuthResponse.oauthToken.refresh_token)
        } catch (e: IGSessionExceptions) {
            LOGGER.info("Failed to refresh OAuth token: ${e.message}")
        }
        oAuthResponse.oauthToken.setExpiresDateTime()
        return oAuthResponse
    }

    @Throws(IGSessionExceptions::class)
    fun getStreamTokens(config: ApplicationConfig) : StreamTokens {
        val session = getOAuthDetails(config)

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(config.ig.endpoints.streamToken))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .header("IG-ACCOUNT-ID", session.accountId)
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .GET()
                .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 200) {
            return StreamTokens(
                    cst = response.headers().firstValue("cst").get()
                    , xSecurityToken = response.headers().firstValue("x-security-token").get()
                    , streamEndpoint = session.lightstreamerEndpoint
            )
        }

        if (response.statusCode() == 400) {
            LOGGER.warn("Could not connect to /fetchSessionTokens. Check credentials in application-credentials.yml ")
            throw IGSessionExceptions(response.statusCode())
        }

        if (response.statusCode() == 403) {
            LOGGER.warn("Could not connect to /fetchSessionTokens. Check api-key in application-credentials.yml ")
            throw IGSessionExceptions(response.statusCode())
        }

        throw IGSessionExceptions(response.statusCode())
    }

    @Throws(IGSessionExceptions::class)
    private fun oAuthLogin(): OauthResponse {

        val json = Gson().toJson(OAuthLogin(config.ig.credentials.username, config.ig.credentials.password))
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(config.ig.endpoints.session))
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .header("Version", "3")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 400) {
            LOGGER.warn("Could not connect to /session. Check credentials in application-credentials.yml ")
            throw IGSessionExceptions(response.statusCode())
        }

        if (response.statusCode() == 403) {
            LOGGER.warn("Could not connect to /session. Check api-key in application-credentials.yml ")
            throw IGSessionExceptions(response.statusCode())
        }

        if (response.statusCode() != 200) {
            throw IGSessionExceptions(response.statusCode())
        }
        return Gson().fromJson(response.body(), OauthResponse::class.java) as OauthResponse
    }

    private fun refreshOAuthToken(refreshToken: String): OauthToken {

        val json = Gson().toJson(RefreshToken(refreshToken))
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(config.ig.endpoints.refreshToken))
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200) {
            throw IGSessionExceptions(response.statusCode())
        }
        return Gson().fromJson(response.body(), OauthToken::class.java)
    }
}




package com.akeijser.igtrader.api.tokens

import com.akeijser.igtrader.api.http.HttpRequestBuilder
import com.akeijser.igtrader.domain.OauthToken
import com.akeijser.igtrader.domain.RequestBuildType
import com.akeijser.igtrader.dto.OauthResponseDTO
import com.akeijser.igtrader.dto.StreamToken
import com.akeijser.igtrader.dto.StreamTokenResponseDTO
import com.akeijser.igtrader.utils.IGSessionExceptions
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.http.HttpClient
import java.net.http.HttpResponse
import java.time.LocalDateTime

@Component
class Tokens(private val httpRequestBuilder: HttpRequestBuilder) {

    private val LOGGER: Logger = LoggerFactory.getLogger(Tokens::class.java)

    @Autowired
    private val tokenRepository = TokensRepository()

    private var oAuthToken : OauthToken? = null
    private var streamToken : StreamToken? = null
    private var lightStreamerEndpoint : String? = null

    fun getOAuthToken(): OauthToken {

        if ( oAuthToken == null || oAuthToken!!.expiresDateTime < LocalDateTime.now() ) {
            oAuthToken = oauthLogin()
        }
        return oAuthToken as OauthToken
    }

    fun getStreamToken(): StreamToken {

        if (streamToken != null) {
            return streamToken as StreamToken
        }

        val client = HttpClient.newBuilder().build()
        val request = httpRequestBuilder.build(RequestBuildType.STREAMTOKEN, oauthToken = getOAuthToken())
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

        val streamTokenResponse = Gson().fromJson(response.body(), StreamTokenResponseDTO::class.java)
        return StreamToken(
                cst = response.headers().firstValue("cst").get(), xSecurityToken = response.headers().firstValue("x-security-token").get(), streamEndpoint = streamTokenResponse.lightstreamerEndpoint
        )
    }

    fun getLightStreamerEndPoint():String {
        if (lightStreamerEndpoint.isNullOrEmpty()){
            val request = httpRequestBuilder.build(RequestBuildType.OAUTHLOGIN)
            val client = HttpClient.newBuilder().build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())

            if (response.statusCode() == 400) {
                throw IGSessionExceptions(response.statusCode())
            }

            if (response.statusCode() == 403) {
                throw IGSessionExceptions(response.statusCode())
            }

            if (response.statusCode() != 200) {
                throw IGSessionExceptions(response.statusCode())
            }

            val oAuthResponse = Gson().fromJson(response.body(), OauthResponseDTO::class.java)
            lightStreamerEndpoint = oAuthResponse.lightstreamerEndpoint

            return lightStreamerEndpoint as String
        }
        return lightStreamerEndpoint as String
    }

    @Throws(IGSessionExceptions::class)
    fun oauthLogin(): OauthToken {
        val request = httpRequestBuilder.build(RequestBuildType.OAUTHLOGIN)
        val client = HttpClient.newBuilder().build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 400) {
            throw IGSessionExceptions(response.statusCode())
        }

        if (response.statusCode() == 403) {
            throw IGSessionExceptions(response.statusCode())
        }

        if (response.statusCode() != 200) {
            throw IGSessionExceptions(response.statusCode())
        }

        val oAuthResponse = Gson().fromJson(response.body(), OauthResponseDTO::class.java)
        lightStreamerEndpoint = oAuthResponse.lightstreamerEndpoint

        return tokenRepository.insertOAuthToken(OauthToken(oAuthResponse.oauthTokenDTO))
    }
}

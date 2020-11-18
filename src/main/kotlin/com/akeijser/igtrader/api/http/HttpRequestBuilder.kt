package com.akeijser.igtrader.api.http

import com.akeijser.igtrader.api.tokens.Tokens
import com.akeijser.igtrader.configuration.ApplicationConfig
import com.akeijser.igtrader.domain.OauthToken
import com.akeijser.igtrader.domain.RequestBuildType
import com.akeijser.igtrader.dto.OAuthLogin
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.Exception
import java.net.URI
import java.net.http.HttpRequest

@Component
class HttpRequestBuilder (private val config: ApplicationConfig) {

    companion object {
        private val LOGGER : Logger = LoggerFactory.getLogger(HttpRequestBuilder::class.java)
    }
    fun build(requestBuildType: RequestBuildType, uriParameters: String = "", oauthToken: OauthToken? = null): HttpRequest {
        when(requestBuildType) {

            RequestBuildType.PRICEDETAILS -> {

                if (oauthToken == null){
                    val e = "Can't build $requestBuildType. No oauthToken passed"
                    LOGGER.warn(e)
                    throw Exception(e)
                }
                if (uriParameters.isEmpty()) {
                    val e = "Can't build $requestBuildType. No URI parameters provided"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                return HttpRequest.newBuilder()
                        .uri(URI.create("${config.ig.endpoints.prices}/${uriParameters}"))
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                        .header("Version", "2")
                        .header("Authorization", "${oauthToken.token_type} ${oauthToken.access_token}")
                        .header("IG-ACCOUNT-ID", config.ig.account.id)
                        .build()
            }
            RequestBuildType.PRICEDETAILSWITHDATE -> {

                if (oauthToken == null){
                    val e = "Can't build $requestBuildType. No oauthToken passed"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                if (uriParameters.isEmpty()) {
                    val e = "Can't build $requestBuildType. No URI parameters provided"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                return HttpRequest.newBuilder()
                        .uri(URI.create("${config.ig.endpoints.prices}/${uriParameters}"))
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                        .header("Version", "3")
                        .header("Authorization", "${oauthToken.token_type} ${oauthToken.access_token}")
                        .header("IG-ACCOUNT-ID", config.ig.account.id)
                        .build()
            }
            RequestBuildType.OAUTHLOGIN -> {

                val json = Gson().toJson(OAuthLogin(config.ig.credentials.username, config.ig.credentials.password))
                return HttpRequest.newBuilder()
                        .uri(URI.create(config.ig.endpoints.session))
                        .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                        .header("Version", "3")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build()
            }
            RequestBuildType.STREAMTOKEN -> {

                if (oauthToken == null){
                    val e = "Can't build $requestBuildType. No oauthToken passed"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                return HttpRequest.newBuilder()
                        .uri(URI.create(config.ig.endpoints.streamToken))
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                        .header("IG-ACCOUNT-ID", config.ig.account.id)
                        .header("Authorization", "${oauthToken.token_type} ${oauthToken.access_token}")
                        .GET()
                        .build()
            }
            RequestBuildType.MARKETNAVIGATION -> {

                if (oauthToken == null){
                    val e = "Can't build $requestBuildType. No oauthToken passed"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                return HttpRequest.newBuilder()
                        .uri(URI.create(config.ig.endpoints.marketNavigation))
                        .header("Content-Type",  "application/json; charset=UTF-8")
                        .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                        .header("IG-ACCOUNT-ID", config.ig.account.id)
                        .header("Authorization", "${oauthToken.token_type} ${oauthToken.access_token}")
                        .header("Version", "1")
                        .build()
            }
            RequestBuildType.NODENAVIGATION -> {

                if (oauthToken == null){
                    val e = "Can't build $requestBuildType. No oauthToken passed"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                if (uriParameters.isEmpty()) {
                    val e = "Can't build $requestBuildType. No URI parameters provided"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                return HttpRequest.newBuilder()
                        .uri(URI.create("${config.ig.endpoints.nodeNavigation}$uriParameters"))
                        .header("Content-Type",  "application/json; charset=UTF-8")
                        .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                        .header("IG-ACCOUNT-ID", config.ig.account.id)
                        .header("Authorization", "${oauthToken.token_type} ${oauthToken.access_token}")
                        .build()
            }
            RequestBuildType.MARKETDETAILS -> {
                if (oauthToken == null){
                    val e = "Can't build $requestBuildType. No oauthToken passed"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                if (uriParameters.isEmpty()) {
                    val e = "Can't build $requestBuildType. No URI parameters provided"
                    LOGGER.warn(e)
                    throw Exception(e)
                }

                return HttpRequest.newBuilder()
                        .uri(URI.create("${config.ig.endpoints.marketSearch}${uriParameters}"))
                        .header("Content-Type",  "application/json; charset=UTF-8")
                        .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                        .header("Version", "2")
                        .header("Authorization", "${oauthToken.token_type} ${oauthToken.access_token}")
                        .header("IG-ACCOUNT-ID", config.ig.account.id)
                        .build()
            }
        }
    }
}




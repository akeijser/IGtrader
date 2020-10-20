package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.configuration.ApplicationConfig
import com.google.gson.Gson
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


@Component
class LoginClient(val config: ApplicationConfig) {

    fun oAuthLogin(): OauthResponse {

        val json = Gson().toJson(OAuthLogin(config.ig.credentials.username, config.ig.credentials.password))
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(config.ig.endpoints.session))
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .header("Version", "3")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return Gson().fromJson(response.body(), OauthResponse::class.java) as OauthResponse
    }

    fun refreshToken(refreshToken: String): OauthToken {

        val json = Gson().toJson(RefreshToken(refreshToken))
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(config.ig.endpoints.refreshToken))
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return Gson().fromJson(response.body(), OauthToken::class.java)
    }
}
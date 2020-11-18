package com.akeijser.igtrader.api.http

import com.akeijser.igtrader.domain.IGHttpResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class HttpSender {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(HttpSender::class.java)
    }

    fun send(request: HttpRequest): IGHttpResponse {
        val client = HttpClient.newBuilder().build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200 ){
            //todo add exception
            LOGGER.info("Failed to get PriceDetails: ${response.body()}")
        }

        return IGHttpResponse(
                responseCode = response.statusCode(),
                headers = response.headers().map(),
                body = response.body())
    }
}
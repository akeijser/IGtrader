package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.configuration.ApplicationConfig
import com.akeijser.igtrader.domain.PricesDetails
import com.akeijser.igtrader.repository.InstrumentDBO
import com.akeijser.igtrader.repository.MarketsRepository
import com.akeijser.igtrader.utils.toInstantWithDefaultZondeId
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter

@Component
class PricesClient(private val config: ApplicationConfig, private val marketsClient: MarketsClient, private val marketsRepository: MarketsRepository) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PricesClient::class.java)
    }

    fun prices(epic: String, resolution: ResolutionDTO = ResolutionDTO.MINUTE, dataPoints: Int = 10): List<PricesDetails>?{
        val instrument = marketsRepository.findInstrument(epic) ?: marketsClient.getInstrument(epic) ?.let { marketsRepository.insertInstrument(InstrumentDBO(it)) } ?: return null

        val session = RestSession.getOAuthDetails(config)
        val uriParameters = "${epic}/${resolution}/${dataPoints}"
        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
                .uri(URI.create("${config.ig.endpoints.prices}/${uriParameters}"))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .header("Version", "2")
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .header("IG-ACCOUNT-ID", session.accountId)
                .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200 ){
            LOGGER.info("get prices for epic ${epic} response headers: ${response.headers()}")
            LOGGER.info("get prices for epic ${epic} response body: ${response.body()}")
        }
        val pricesDTO = Gson().fromJson(response.body(), PricesDTO::class.java)

        return pricesDTO.pricesDetails?.map { PricesDetails(instrument, it) }?.toList()
    }

    fun prices(epic: String, resolution: ResolutionDTO = ResolutionDTO.MINUTE, startDate: LocalDateTime, endDate: LocalDateTime): List<PricesDetails>? {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                .withZone(UTC)

        val startDateString = formatter.format( startDate.toInstantWithDefaultZondeId() ).replace(":", "%3A")
        val endDateString = formatter.format( endDate.toInstantWithDefaultZondeId() ).replace(":", "%3A")
        return prices(epic, resolution, startDateString, endDateString)
    }
    //yyyy-MM-dd HH:mm:ss
    fun prices(epic: String, resolution: ResolutionDTO = ResolutionDTO.MINUTE, startDate: String, endDate: String): List<PricesDetails>? {
        val instrument = marketsClient.getInstrument(epic) ?: return null

        val session = RestSession.getOAuthDetails(config)
        val uriParameters = "${epic}?resolution=${resolution}&from${startDate}&to${endDate}"

        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
                .uri(URI.create("${config.ig.endpoints.prices}/${uriParameters}"))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .header("Version", "3")
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .header("IG-ACCOUNT-ID", session.accountId)
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200 ){
            LOGGER.info("get prices for epic $epic response headers: ${response.headers()}")
            LOGGER.info("get prices for epic $epic response body: ${response.body()}")
        }

        val pricesDTO = Gson().fromJson(response.body(), PricesDTO::class.java)

        return pricesDTO.pricesDetails?.map { PricesDetails(instrument, it) }?.toList()
    }
}
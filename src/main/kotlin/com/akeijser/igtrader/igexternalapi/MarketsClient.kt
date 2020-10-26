package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.configuration.ApplicationConfig
import com.akeijser.igtrader.domain.Epics
import com.akeijser.igtrader.domain.Instrument
import com.akeijser.igtrader.domain.Markets
import com.akeijser.igtrader.domain.MultipleEpicDetails
import com.akeijser.igtrader.repository.MarketsRepository
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class MarketsClient(private val config: ApplicationConfig, private val marketsRepository: MarketsRepository) {

    /**
     * find all Markets. A Markets is a specific catagory of the overal stockmarket
     * For example:
     *  Aandelen - Euronext (Amsterdam)
     *  Aandelen - NASDAQ (VS)
     *  Forex
     *  Cryptocurrency
     */

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MarketsClient::class.java)
    }

    fun getMarkets(): Markets {

        val session = RestSession.getOAuthDetails(config)

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(config.ig.endpoints.marketNavigation))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .header("IG-ACCOUNT-ID", session.accountId)
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200 ){
            LOGGER.info("get markets response headers: ${response.headers()}")
            LOGGER.info("get markets response body: ${response.body()}")
        }
        return Gson().fromJson(response.body(), MultiNodesDTO::class.java).toMarkets()
    }

    /**
     * find all epics for a single market. An Epic is the specific, tradable "stock"
     */
    fun getEpicsForMarket(marketId: Int): Epics {
        val session = RestSession.getOAuthDetails(config)

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create("${config.ig.endpoints.nodeNavigation}$marketId"))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .header("IG-ACCOUNT-ID", session.accountId)
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200 ){
            LOGGER.info("get epics for marketId: $marketId response headers: ${response.headers()}")
            LOGGER.info("get epics for marketId: $marketId response body: ${response.body()}")
        }
        return Gson().fromJson(response.body(), MultiNodesDTO::class.java).toEpics()
    }

    /**
     * Search IG trader platform for a single epic
     */
    fun getEpicDetails(epic: String, filter: MarketSearchFilterDTO = MarketSearchFilterDTO.ALL ): MultipleEpicDetails {
        val epics = listOf(epic)
        return getEpicDetails(epics, filter)
    }

    /**
     * Search IG Trader platform for multiple epics
     */
    fun getEpicDetails(epics: List<String>, filter: MarketSearchFilterDTO = MarketSearchFilterDTO.ALL ): MultipleEpicDetails {

        val uriParameters = "?epics=${epics.joinToString(separator = "%2C", transform = {it.replace(" ", "")})}&filter=${filter.name}"

        val session = RestSession.getOAuthDetails(config)

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create("${config.ig.endpoints.marketSearch}${uriParameters}"))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", config.ig.credentials.apiKey)
                .header("Version", "2")
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .header("IG-ACCOUNT-ID", session.accountId)
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200 ){
            LOGGER.info("get epic details response headers: ${response.headers()}")
            LOGGER.info("get epic details response body: ${response.body()}")
        }
        return Gson().fromJson(response.body(), MultipleEpicDetailsDTO::class.java).toDomain()
    }

    fun getInstrument(epic: String): Instrument? {
        val instrument = marketsRepository.findInstrument(epic)
        return instrument?.let{ instrument } ?: run {
            return getEpicDetails(epic).multipleEpicDetails?.get(0)?.instrument
        }
    }
}






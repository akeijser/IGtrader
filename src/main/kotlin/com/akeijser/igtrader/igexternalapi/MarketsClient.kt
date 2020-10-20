package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.domainobjects.Epics
import com.akeijser.igtrader.domainobjects.Instrument
import com.akeijser.igtrader.domainobjects.Markets
import com.akeijser.igtrader.domainobjects.MultipleEpicDetails
import com.akeijser.igtrader.repository.MarketsRepository
import com.google.gson.Gson
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

@Component
class MarketsClient(private val loginClient: LoginClient, private val marketsRepository: MarketsRepository) {

    /**
     * find all Markets. A Markets is a specific catagory of the overal stockmarket
     * For example:
     *  Aandelen - Euronext (Amsterdam)
     *  Aandelen - NASDAQ (VS)
     *  Forex
     *  Cryptocurrency
     */
    fun getMarkets(): Markets {

        val session = Session.getSession(loginClient)

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(loginClient.config.ig.endpoints.marketNavigation))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", loginClient.config.ig.credentials.apiKey)
                .header("IG-ACCOUNT-ID", session.accountId)
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return Gson().fromJson(response.body(), MultiNodesDTO::class.java).toMarkets()
    }

    /**
     * find all epics for a single market. An Epic is the specific, tradable "stock"
     */
    fun getEpicsForMarket(marketId: Int): Epics {
        val session = Session.getSession(loginClient)

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create("${loginClient.config.ig.endpoints.nodeNavigation}$marketId"))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", loginClient.config.ig.credentials.apiKey)
                .header("IG-ACCOUNT-ID", session.accountId)
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
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

        val session = Session.getSession(loginClient)

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create("${loginClient.config.ig.endpoints.marketSearch}${uriParameters}"))
                .header("Content-Type",  "application/json; charset=UTF-8")
                .header("X-IG-API-KEY", loginClient.config.ig.credentials.apiKey)
                .header("Version", "2")
                .header("Authorization", "${session.oauthToken.token_type} ${session.oauthToken.access_token}")
                .header("IG-ACCOUNT-ID", session.accountId)
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return Gson().fromJson(response.body(), MultipleEpicDetailsDTO::class.java).toDomain()
    }

    fun getInstrument(epic: String): Instrument? {
        val instrument = marketsRepository.findInstrument(epic)
        return instrument?.let{ instrument } ?: run {
            return getEpicDetails(epic).multipleEpicDetails?.get(0)?.instrument
        }
    }

    fun getInstrumentId(epic: String): UUID? {
        val instrument = marketsRepository.findInstrument(epic)
        return instrument?.let{ instrument.id } ?: run {
            return getEpicDetails(epic).multipleEpicDetails?.get(0)?.instrument?.id
        }
    }
}






package com.akeijser.igtrader.api.epics

import com.akeijser.igtrader.api.http.HttpRequestBuilder
import com.akeijser.igtrader.api.http.HttpSender
import com.akeijser.igtrader.api.tokens.Tokens
import com.akeijser.igtrader.domain.Epic
import com.akeijser.igtrader.domain.EpicDetail
import com.akeijser.igtrader.domain.RequestBuildType
import com.akeijser.igtrader.dto.MarketSearchFilterDTO
import com.akeijser.igtrader.dto.MultiNodesDTO
import com.akeijser.igtrader.dto.MultipleEpicDetailsDTO
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EpicServiceImpl(private val tokens: Tokens,
                      private val httpRequestBuilder: HttpRequestBuilder,
                      private val httpSender: HttpSender) : EpicService {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(HttpSender::class.java)
    }

    //todo returning Epics, need to be changed. I dont need class Epics. I should just be List<Epic>
    @Throws
    override fun getEpicsForMarketId(marketId: Int): List<Epic> {
        val token = tokens.getOAuthToken()
        val request = httpRequestBuilder.build(RequestBuildType.NODENAVIGATION, uriParameters = marketId.toString(), oauthToken = token)

        val response = httpSender.send(request)

        if (response.responseCode != 200 ){
            LOGGER.info("get epics for marketId: $marketId response headers: ${response.headers}")
            LOGGER.info("get epics for marketId: $marketId response body: ${response.body}")
        }

        val responseEpicDTO = Gson().fromJson(response.body, MultiNodesDTO::class.java)

        if (responseEpicDTO.nodes == null) {
            throw Exception("No epics found for marketId: $marketId")
        }

        return responseEpicDTO.nodes.map { epic -> Epic(epic.name, marketId ) }
    }
}
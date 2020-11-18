package com.akeijser.igtrader.api.markets

import com.akeijser.igtrader.api.http.HttpRequestBuilder
import com.akeijser.igtrader.api.tokens.Tokens
import com.akeijser.igtrader.api.http.HttpSender
import com.akeijser.igtrader.domain.*
import com.akeijser.igtrader.dto.MultiNodesDTO
import com.akeijser.igtrader.utils.IGSessionExceptions
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class MarketsServiceImpl(private val marketsRepository: MarketsRepository,
                         private val tokens: Tokens,
                         private val httpRequestBuilder: HttpRequestBuilder,
                         private val httpSender : HttpSender): MarketsService {

    /**
     * find all Markets. A Markets is a specific catagory of the overal stockmarket
     * For example:
     *  Aandelen - Euronext (Amsterdam)
     *  Aandelen - NASDAQ (VS)
     *  Forex
     *  Cryptocurrency
     */

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MarketsServiceImpl::class.java)
    }

    @Throws
    override fun getMarkets(): List<Market> {
        val token: OauthToken
        try {
             token = tokens.getOAuthToken()
        } catch (e : IGSessionExceptions) {
            LOGGER.info("could not getMarkets because: ${e.message}")
            throw e
        }

        val request = httpRequestBuilder.build(RequestBuildType.MARKETNAVIGATION, oauthToken = token)

        //todo error handling when not 200
        val response = httpSender.send(request)
        if (response.responseCode != 200 ){
            LOGGER.info("get markets response headers: ${response.headers}")
            LOGGER.info("get markets response body: ${response.body}")
        }

        //TODO: Set actual error
        try {
            return Gson().fromJson(response.body, MultiNodesDTO::class.java).toMarkets()
        } catch (e : Exception) {
            throw e
        }
    }
}






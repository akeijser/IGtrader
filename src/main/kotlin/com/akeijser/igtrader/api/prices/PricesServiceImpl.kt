package com.akeijser.igtrader.api.prices

import com.akeijser.igtrader.api.tokens.Tokens
import com.akeijser.igtrader.api.http.HttpRequestBuilder
import com.akeijser.igtrader.api.http.HttpSender
import com.akeijser.igtrader.api.instruments.InstrumentRepository
import com.akeijser.igtrader.api.instruments.InstrumentService
import com.akeijser.igtrader.domain.PriceRequest
import com.akeijser.igtrader.domain.PricesDetails
import com.akeijser.igtrader.domain.RequestBuildType
import com.akeijser.igtrader.dto.PricesDTO
import com.akeijser.igtrader.dto.ResolutionDTO
import com.akeijser.igtrader.domain.Instrument
import com.akeijser.igtrader.utils.toInstantWithDefaultZondeId
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter

@Component
class PricesServiceImpl(private val requestBuilder: HttpRequestBuilder
                        , private val httpSender: HttpSender
                        , private val tokens: Tokens
                ) : PricesService{

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PricesServiceImpl::class.java)
    }

    override fun getPrices(priceRequest : PriceRequest): List<PricesDetails>? {

        if (priceRequest.startDate != null && priceRequest.endDate != null) {

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                    .withZone(UTC)

            val startDateString = formatter.format( priceRequest.startDate.toInstantWithDefaultZondeId() ).replace(":", "%3A")
            val endDateString = formatter.format( priceRequest.endDate.toInstantWithDefaultZondeId() ).replace(":", "%3A")

            return prices(priceRequest.epic, priceRequest.resolution, startDateString, endDateString)
        }

        return prices(priceRequest.epic, priceRequest.resolution, priceRequest.dataPoints)
    }

    private fun prices(epic: String, resolution: ResolutionDTO = ResolutionDTO.MINUTE, dataPoints: Int = 10): List<PricesDetails>?{

        val uriParameters = "${epic}/${resolution}/${dataPoints}"
        val token = tokens.getOAuthToken()
        val request = requestBuilder.build(RequestBuildType.PRICEDETAILS, uriParameters, token)

        val response = httpSender.send(request)

        val priceResponse = Gson().fromJson(response.body, PricesDTO::class.java)
        return priceResponse.pricesDetails.map { PricesDetails( it, epic) }.toList()
    }

    /**
     * Get Prices with date Strings
     * yyyy-MM-dd HH:mm:ss
     */
    private fun prices(epic: String, resolution: ResolutionDTO = ResolutionDTO.MINUTE, startDate: String, endDate: String): List<PricesDetails>? {
        val uriParameters = "${epic}?resolution=${resolution}&from${startDate}&to${endDate}"

        val token = tokens.getOAuthToken()
        val request = requestBuilder.build(RequestBuildType.PRICEDETAILSWITHDATE, uriParameters, token)
        val response = httpSender.send(request)

        val pricesDTO = Gson().fromJson(response.body, PricesDTO::class.java)

        return pricesDTO.pricesDetails.map { PricesDetails( it, epic) }.toList()
    }
}
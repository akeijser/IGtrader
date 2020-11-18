package com.akeijser.igtrader.api.prices

import com.akeijser.igtrader.api.testhelpers.InstrumentFixture
import com.akeijser.igtrader.api.testhelpers.OauthTokenFixture
import com.akeijser.igtrader.api.testhelpers.PricesDTOFixture
import com.akeijser.igtrader.api.http.HttpRequestBuilder
import com.akeijser.igtrader.api.http.HttpSender
import com.akeijser.igtrader.api.instruments.InstrumentRepository
import com.akeijser.igtrader.api.instruments.InstrumentService
import com.akeijser.igtrader.api.markets.MarketsServiceImpl
import com.akeijser.igtrader.configuration.ApplicationConfig
import com.akeijser.igtrader.domain.PricesDetails
import com.akeijser.igtrader.domain.RequestBuildType
import com.akeijser.igtrader.dto.ResolutionDTO
import com.akeijser.igtrader.api.markets.MarketsRepository
import com.akeijser.igtrader.api.tokens.Tokens
import com.akeijser.igtrader.domain.IGHttpResponse
import com.akeijser.igtrader.domain.PriceRequest
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.URI
import java.net.http.HttpRequest


internal class PricesServiceImplTest(private val instrumentRepository : InstrumentRepository) {

    private val config: ApplicationConfig = mockk()
    private val marketsServiceImpl: MarketsServiceImpl = mockk()
    private val marketsRepository: MarketsRepository = mockk()
    private val httpRequestBuilder : HttpRequestBuilder = mockk()
    private val httpSender : HttpSender = mockk()
    private val tokens : Tokens = mockk()
    private val instrumentService : InstrumentService = mockk()

    private val pricesService = PricesServiceImpl( httpRequestBuilder, httpSender, tokens )

    @Nested
    inner class `Test prices`{

//        @BeforeEach
//        fun setup() {
//
//            every { config.ig.credentials.username } answers {"username"}
//            every { config.ig.credentials.password } answers {"password"}
//            every { config.ig.credentials.apiKey } answers {"apiKey"}
//            every { config.ig.endpoints.session } answers {"https://demo-api.ig.com/gateway/deal/session"}
//            every { config.ig.endpoints.prices } answers {"https://demo-api.ig.com/gateway/deal/prices"}
//            every { config.ig.account.id } answers {"Z3AFFA"}
//
//            every { marketsRepository.findInstrument(any())} returns InstrumentFixture.create()
//        }
//
//        @Test
//        fun `Get the prices for an epic`() {
//
//            //find the instrument related to the epic
//            val epic = "CS.D.BITCOIN.CFD.IP"
//            val instrument = InstrumentFixture.create()
//            every { marketsRepository.findInstrument(epic) } returns (instrument)
//            every { instrumentService.getInstrument(epic) } returns (instrument)
//
//            //set the uriParameters
//            val resolution = ResolutionDTO.HOUR
//            val dataPoints = 10
//            val uriParameters = "${epic}/${resolution}/${dataPoints}"
//
//            //get an oauthtoken
//            val oauthToken = OauthTokenFixture.create()
//            every { tokens.getOAuthToken() } answers {oauthToken}
//
//            //this will also do igTokens.oauthLogin()  might need to mockk that too
//
//            //build a priceDetailHttpRequest
//            val request = HttpRequest.newBuilder()
//                    .uri(URI.create("${config.ig.endpoints.prices}/${uriParameters}"))
//                    .header("Content-Type", "application/json; charset=UTF-8")
//                    .header("X-IG-API-KEY", config.ig.credentials.apiKey)
//                    .header("Version", "2")
//                    .header("Authorization", "${oauthToken.token_type} ${oauthToken.access_token}")
//                    .header("IG-ACCOUNT-ID", config.ig.account.id)
//                    .build()
//            every { httpRequestBuilder.build(requestBuildType = RequestBuildType.PRICEDETAILS, uriParameters = uriParameters, oauthToken = oauthToken) } answers {request}
//
//            //send the request
//            val pricesDto = PricesDTOFixture.create()
//
//            val json = Gson().toJson(pricesDto)
//
//            val httpResponse = IGHttpResponse(200, emptyMap(), json)
//
//            every {httpSender.send(request)} returns (httpResponse)
//
//            val priceDetails = pricesDto.pricesDetails.map { PricesDetails(instrument, it) }.toList()
//
//            //Execute
//            val priceRequest = PriceRequest(epic = epic, resolution = ResolutionDTO.HOUR, dataPoints = 10)
//            val result = pricesService.getPrices(priceRequest)
//
//            assertThat(result).isEqualTo(priceDetails)
//        }
    }

}
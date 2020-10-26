package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.AbstractFeatureTest
import com.akeijser.igtrader.repository.MarketsRepository
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

internal class PricesClientTest: AbstractFeatureTest() {

    @Autowired
    private val marketRepository = MarketsRepository()

    @Autowired
    private val marketsClient = MarketsClient(config, marketRepository )

    @Autowired
    private val pricesClient = PricesClient(config, marketsClient, marketRepository)

    @Test
    fun pricesTest(){
        println(pricesClient.prices("CS.D.BITCOIN.CFD.IP", ResolutionDTO.HOUR, 100))
    }

    @Test
    fun pricesTestLocalDateTime(){
        println(pricesClient.prices("CS.D.BITCOIN.CFD.IP", ResolutionDTO.HOUR, LocalDateTime.now().minusDays(1), LocalDateTime.now()))
    }
}
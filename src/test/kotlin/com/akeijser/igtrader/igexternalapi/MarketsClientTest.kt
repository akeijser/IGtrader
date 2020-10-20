package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.AbstractFeatureTest
import com.akeijser.igtrader.repository.MarketsRepository
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired

internal class MarketsClientImplTest: AbstractFeatureTest() {

    @Autowired
    private val loginClient = LoginClient(config)
    @Autowired
    private val marketRepository = MarketsRepository()
    @Autowired
    private val marketsClient = MarketsClient(loginClient, marketRepository)

    @Test
    fun browseMarkets() {
        println(marketsClient.getMarkets().toString())
    }

    @Test
    fun browseEpics() {
        println(marketsClient.getEpicsForMarket(668394))
    }

    @Test
    fun marketSearch(){
        println(marketsClient.getEpicDetails("CS.D.BITCOIN.CFD.IP"))
    }

    @Test
    fun marketSearchMultipleEpics(){
        println(marketsClient.getEpicDetails(listOf("CS.D.BITCOIN.CFE.IP", "CS.D.BITCOIN.CFD.IP"), MarketSearchFilterDTO.SNAPSHOT_ONLY))
    }

    @Test
    fun findEpicTest(){
        println(marketsClient.getInstrument("CS.D.BITCOIN.CFE.IP"))
    }


}
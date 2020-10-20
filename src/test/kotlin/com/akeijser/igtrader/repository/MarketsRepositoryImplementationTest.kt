package com.akeijser.igtrader.repository

import com.akeijser.igtrader.igexternalapi.LoginClient
import com.akeijser.igtrader.igexternalapi.MarketsClient
import org.junit.jupiter.api.Test
import com.akeijser.igtrader.AbstractFeatureTest
import com.akeijser.igtrader.domainobjects.Instrument

import org.springframework.beans.factory.annotation.Autowired

internal class MarketsRepositoryImplementationTest: AbstractFeatureTest() {

    @Autowired
    private val loginClient = LoginClient(config)
    @Autowired
    private val marketRepository = MarketsRepository()
    @Autowired
    private val marketsClient = MarketsClient(loginClient, marketRepository)

    @Test
    fun insertMarketImplTest() {
        val markets = marketsClient.getMarkets()
        markets.markets?.forEach {
            marketRepository.insertMarket(it.toDBO())
        }
    }

    @Test
    fun insertEpicImplTest() {
        val epics = marketsClient.getEpicsForMarket(668394)
        epics.nodes?.forEach {
            marketRepository.insertEpic(it.toDBO())
        }
    }

    @Test
    fun insertMultipleEpicDetails(){
        val epicDetails = marketsClient.getEpicDetails(listOf("CS.D.BITCOIN.CFE.IP", "CS.D.BITCOIN.CFD.IP"))
        epicDetails.multipleEpicDetails?.forEach {
            marketRepository.insertEpicDetails(it.toDbo())
        }
    }

    @Test
    fun findInstrumentTest() {
        val epicDetails = marketsClient.getEpicDetails("CS.D.BITCOIN.CFD.IP")
        val instrument = epicDetails.multipleEpicDetails?.get(0)?.instrument
        if (instrument != null) {
            val instrumentDBO = InstrumentDBO(instrument)
            marketRepository.insertInstrument(instrumentDBO)
        }
        println("bbalbalbablablad${marketRepository.findInstrument("CS.D.BITCOIN.CFD.IP").toString()}")
    }
}
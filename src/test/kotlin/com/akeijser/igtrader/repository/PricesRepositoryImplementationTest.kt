package com.akeijser.igtrader.repository

import com.akeijser.igtrader.AbstractFeatureTest
import com.akeijser.igtrader.igexternalapi.MarketsClient
import com.akeijser.igtrader.igexternalapi.PricesClient
import com.akeijser.igtrader.igexternalapi.ResolutionDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PricesRepositoryImplementationTest : AbstractFeatureTest() {

    @Autowired
    private val marketRepository = MarketsRepository()

    @Autowired
    private val marketsClient = MarketsClient(config, marketRepository)

    @Autowired
    private val pricesClient = PricesClient(config, marketsClient, marketRepository)

    @Autowired
    private val pricesRepository = PricesRepository(marketRepository)

    @Test
    fun insertPricesTest(){
        val prices = pricesClient.prices("CS.D.BITCOIN.CFD.IP", ResolutionDTO.HOUR, 100)

        prices?.forEach {
            pricesRepository.insertPriceDetails(PriceDetailsDBO(it))
        }
    }
}
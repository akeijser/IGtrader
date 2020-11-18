package com.akeijser.igtrader.repository

import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import com.akeijser.igtrader.dbo.PriceDetailsDBO
import com.akeijser.igtrader.dto.ResolutionDTO
import com.akeijser.igtrader.api.prices.PriceDetailsRepository
import com.akeijser.igtrader.api.prices.PricesService
import com.akeijser.igtrader.domain.PriceRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PricesRepositoryIntegrationTest(
        @Autowired private val pricesService: PricesService,
        @Autowired private val priceDetailsRepository: PriceDetailsRepository) : AbstractFeatureTest() {


    @Test
    fun insertPricesTest(){
        val priceRequest = PriceRequest(epic = "CS.D.BITCOIN.CFD.IP", resolution = ResolutionDTO.HOUR, dataPoints = 10)
        val prices = pricesService.getPrices(priceRequest)

        prices?.forEach {
            priceDetailsRepository.save(PriceDetailsDBO(it))
        }
    }
}
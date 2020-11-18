package com.akeijser.igtrader.repository

import com.akeijser.igtrader.api.prices.PriceDetailsRepository
import com.akeijser.igtrader.api.prices.PricesService
import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import com.akeijser.igtrader.api.testhelpers.PriceDetailsDTOFixture
import com.akeijser.igtrader.api.testhelpers.PriceDetailsFixture
import com.akeijser.igtrader.dbo.PriceDetailsDBO
import com.akeijser.igtrader.domain.PriceRequest
import com.akeijser.igtrader.domain.PricesDetails
import com.akeijser.igtrader.dto.ResolutionDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PriceDetailsRepositoryTest(@Autowired private val priceDetailsRepository: PriceDetailsRepository,
                                          @Autowired private val pricesService: PricesService): AbstractFeatureTest() {

    @BeforeEach
    fun reset(){
        priceDetailsRepository.deleteAll()
    }

    @Test
    fun `test save and find`() {

        //Prepare
        val priceDetails = PriceDetailsDBO(PriceDetailsFixture.create())

        //Execute
        priceDetailsRepository.save(priceDetails)

        //Assert
        val result = priceDetailsRepository.findAll()

        assertThat(result).isNotEmpty

    }

    @Test
    fun `Insert live prices`(){

        //Prepare
        val priceRequest = PriceRequest(epic = "CS.D.BITCOIN.CFD.IP", resolution = ResolutionDTO.HOUR, dataPoints = 10)
        val prices = pricesService.getPrices(priceRequest)

        prices?.forEach {
            priceDetailsRepository.save(PriceDetailsDBO(it))
        }
    }
}
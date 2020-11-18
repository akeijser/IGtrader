package com.akeijser.igtrader.repository

import org.junit.jupiter.api.Test
import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import com.akeijser.igtrader.api.markets.MarketsRepository
import com.akeijser.igtrader.api.testhelpers.MarketFixture
import com.akeijser.igtrader.api.testhelpers.ResetMarket
import com.akeijser.igtrader.dbo.MarketDBO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.springframework.beans.factory.annotation.Autowired

internal class MarketsRepositoryIntegrationTest(@Autowired private val marketRepository : MarketsRepository): AbstractFeatureTest() {

    @Autowired
    private val resetDB = ResetMarket()


    @BeforeEach
    fun resetDB(){
        resetDB.reset()
    }

    @Test
    fun `Test save and find`() {

        //Prepare
        val market = MarketFixture.create()

        //Execute
        marketRepository.save(MarketDBO(market))

        //Assert
        assertThat(marketRepository.findAll()).isNotEmpty

    }
}
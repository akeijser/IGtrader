package com.akeijser.igtrader.repository

import com.akeijser.igtrader.api.epics.EpicRepository
import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import com.akeijser.igtrader.api.epics.EpicService
import com.akeijser.igtrader.api.markets.MarketsRepository
import com.akeijser.igtrader.api.markets.MarketsService
import com.akeijser.igtrader.api.testhelpers.EpicFixture
import com.akeijser.igtrader.dbo.EpicDBO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class EpicRepositoryIntegrationTest (@Autowired private val epicRepository: EpicRepository): AbstractFeatureTest() {

    @BeforeEach
    fun reset(){
        epicRepository.deleteAll()
    }

    @Test
    fun `save and find`(){

        val epic = EpicDBO(EpicFixture.create())

        epicRepository.save(epic)

        val result = epicRepository.findAll()
        assertThat(result).isNotEmpty
    }
}

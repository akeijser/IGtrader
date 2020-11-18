package com.akeijser.igtrader.api.instrument

import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import com.akeijser.igtrader.api.instruments.InstrumentService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class InstrumentServiceIntegrationTest(@Autowired private val instrumentService: InstrumentService) : AbstractFeatureTest(){


    @Nested
    inner class `Use IG API to find information about instruments`{
        @Test
        fun findEpicTest(){

            val instrument = instrumentService.getInstrument("CS.D.BITCOIN.CFE.IP")

            assertThat(instrument).isNotNull
        }
    }
}
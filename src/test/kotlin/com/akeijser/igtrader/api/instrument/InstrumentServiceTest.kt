package com.akeijser.igtrader.api.instrument

import com.akeijser.igtrader.api.testhelpers.InstrumentFixture
import com.akeijser.igtrader.api.instruments.InstrumentService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class InstrumentServiceTest {

    private val instrumentService : InstrumentService = mockk()

    @Test
    fun `Test getInstument`(){

        //Prepare
        val epic = "some epic"
        val instrument = InstrumentFixture.create()

        every { instrumentService.getInstrument(epic) } answers {instrument}

        //Execute
        val result = instrumentService.getInstrument(epic)

        //Assert
        assertThat(result).isEqualTo(instrument)
    }
}
package com.akeijser.igtrader.repository

import com.akeijser.igtrader.api.epicdetails.EpicDetailService
import com.akeijser.igtrader.api.instruments.InstrumentRepository
import com.akeijser.igtrader.api.testhelpers.*
import com.akeijser.igtrader.dbo.InstrumentDBO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.PersistenceException

@Component
internal class InstrumentRepositoryIntegrationTest(
        @Autowired private val instrumentRepository: InstrumentRepository
        , @Autowired private val epicDetailService: EpicDetailService

) : AbstractFeatureTest() {

    @Autowired
    private val  resetDB = ResetInstrument()

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(InstrumentRepositoryIntegrationTest::class.java)
    }

    @BeforeEach
    fun resetDB(){
        resetDB.reset()
    }

    @Nested
    inner class `Test Instrument Repository with some real data` {

        //todo make sure found instrument is deleted first
        @Test
        fun `Test insert instrument`(){
            val instrument = InstrumentFixture.create()

            try {
                val instrumentDBO = InstrumentDBO(instrument)
                instrumentRepository.save(instrumentDBO)
            } catch (e: PersistenceException) {
                LOGGER.error(e.localizedMessage)
            }
        }

        @Test
        fun `Test find instrument`(){
            val epicDetails = epicDetailService.getEpicDetails("CS.D.BITCOIN.CFD.IP")
            val instrument = epicDetails?.get(0)?.instrument
            assertThat(instrument).isNotNull
        }
    }
}
package com.akeijser.igtrader.api.instruments

import com.akeijser.igtrader.api.epicdetails.EpicDetailService
import com.akeijser.igtrader.domain.Instrument
import org.springframework.stereotype.Component

@Component
class InstrumentServiceImpl(private val instrumentRepository: InstrumentRepository , private val epicDetailService: EpicDetailService): InstrumentService{

    override fun getInstrument(epicName: String): Instrument? {

        val dbo = instrumentRepository.findByEpic(epicName)
        if (dbo != null) {
            return Instrument(dbo)
        }

        return epicDetailService.getEpicDetails(epicName)?.get(0)?.instrument

    }
}
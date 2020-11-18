package com.akeijser.igtrader.api.instruments

import com.akeijser.igtrader.domain.Instrument

interface InstrumentService {

    fun getInstrument(epicName: String): Instrument?



}
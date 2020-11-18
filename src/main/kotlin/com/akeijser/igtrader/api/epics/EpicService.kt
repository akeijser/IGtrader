package com.akeijser.igtrader.api.epics

import com.akeijser.igtrader.domain.Epic

interface EpicService {

    fun getEpicsForMarketId(marketId: Int): List<Epic>

}
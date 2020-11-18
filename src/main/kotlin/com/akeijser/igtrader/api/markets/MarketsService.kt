package com.akeijser.igtrader.api.markets

import com.akeijser.igtrader.domain.*
import com.akeijser.igtrader.dto.MarketSearchFilterDTO
import org.springframework.stereotype.Service

interface MarketsService {

    fun getMarkets() : List<Market>

}
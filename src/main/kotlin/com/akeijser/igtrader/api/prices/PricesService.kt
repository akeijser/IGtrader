package com.akeijser.igtrader.api.prices

import com.akeijser.igtrader.domain.PriceRequest
import com.akeijser.igtrader.domain.PricesDetails

interface PricesService{

    fun getPrices(priceRequest : PriceRequest): List<PricesDetails>?

}
package com.akeijser.igtrader.api.markets

import com.akeijser.igtrader.dbo.MarketDBO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MarketsRepository: JpaRepository<MarketDBO, UUID>
package com.akeijser.igtrader.api.prices

import com.akeijser.igtrader.dbo.PriceDetailsDBO
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

//@Repository
interface PriceDetailsRepository: JpaRepository<PriceDetailsDBO, UUID>
package com.akeijser.igtrader.api.epicdetails

import com.akeijser.igtrader.dbo.EpicDetailDBO
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EpicDetailRepository: JpaRepository<EpicDetailDBO, UUID>
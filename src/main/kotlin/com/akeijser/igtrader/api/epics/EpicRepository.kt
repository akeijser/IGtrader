package com.akeijser.igtrader.api.epics

import com.akeijser.igtrader.dbo.EpicDBO
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EpicRepository : JpaRepository<EpicDBO, UUID>
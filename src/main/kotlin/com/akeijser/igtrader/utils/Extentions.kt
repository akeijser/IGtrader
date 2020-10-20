package com.akeijser.igtrader.utils

import java.time.LocalDateTime
import java.util.*

//This will get the machines localDataTime. this is asking for problems
//TODO clean this up
fun LocalDateTime.toInstantWithDefaultZondeId() = this.atZone(TimeZone.getDefault().toZoneId()).toInstant()
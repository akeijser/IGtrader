package com.akeijser.igtrader.api.stream

import com.akeijser.igtrader.utils.IGSessionExceptions
import com.lightstreamer.client.LightstreamerClient

interface StreamService {

    @Throws(IGSessionExceptions::class)
    fun getStreamSession(): LightstreamerClient

    fun closeSession()

}
package com.akeijser.igtrader.api.stream

import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class StreamSessionTest(@Autowired private val streamService: StreamService) : AbstractFeatureTest() {

    @Test
    fun getLsClient() {
        streamService.getStreamSession()
    }

    @Test
    fun closeSession() {
    }
}
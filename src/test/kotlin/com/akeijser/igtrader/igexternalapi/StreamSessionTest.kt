package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.AbstractFeatureTest
import org.junit.jupiter.api.Test

internal class StreamSessionTest: AbstractFeatureTest() {

    @Test
    fun getLsClient() {
        StreamSession.connect(config)
    }

    @Test
    fun closeSession() {
    }
}
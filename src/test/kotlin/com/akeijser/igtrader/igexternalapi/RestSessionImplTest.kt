package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.AbstractFeatureTest
import org.junit.jupiter.api.Test

internal class RestSessionImplTest: AbstractFeatureTest(){

    @Test
    fun refreshingTokenTest(){
        println(RestSession.getOAuthDetails(config))
        Thread.sleep(30_000)
        println(RestSession.getOAuthDetails(config))
        Thread.sleep(30_000)
        println(RestSession.getOAuthDetails(config))

    }

    @Test
    fun getSingleTest(){
        println(RestSession.getOAuthDetails(config).accountId)
    }
}


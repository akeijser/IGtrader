package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.AbstractFeatureTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class RestSessionImplTest: AbstractFeatureTest(){

    @Autowired
    val loginWebServiceClient = LoginClient(config)

    @Test
    fun getSessionTest(){
        println(RestSession.getSession(loginWebServiceClient))
        Thread.sleep(30_000)
        println(RestSession.getSession(loginWebServiceClient))
        Thread.sleep(30_000)
        println(RestSession.getSession(loginWebServiceClient))

    }
}


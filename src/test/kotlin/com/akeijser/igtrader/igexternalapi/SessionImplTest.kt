package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.AbstractFeatureTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class SessionImplTest: AbstractFeatureTest(){

    @Autowired
    val loginWebServiceClient = LoginClient(config)


    @Test
    fun getSessionTest(){
        println(Session.getSession(loginWebServiceClient))
        Thread.sleep(30_000)
        println(Session.getSession(loginWebServiceClient))
        Thread.sleep(30_000)
        println(Session.getSession(loginWebServiceClient))

    }
}


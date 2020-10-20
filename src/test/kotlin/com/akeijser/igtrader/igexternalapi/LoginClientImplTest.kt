package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.AbstractFeatureTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


class LoginClientImplTest: AbstractFeatureTest() {

    @Autowired
    val loginClient = LoginClient(config)

     @Test
    fun oAuthLoginTest(){
        println(loginClient.oAuthLogin())
    }
}
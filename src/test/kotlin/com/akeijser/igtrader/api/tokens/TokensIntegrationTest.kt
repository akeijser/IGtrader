package com.akeijser.igtrader.api.tokens

import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import com.akeijser.igtrader.api.http.HttpRequestBuilder
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class TokensIntegrationTest: AbstractFeatureTest() {
    //todo finish this up nicely

    @Autowired
    private val httpRequestBuilder = HttpRequestBuilder(config)

    @Autowired
    private val igTokens = Tokens(httpRequestBuilder)

    @Nested
    inner class `Use IGToken class to find oauthToken`{
        @Test
        fun getOAuthToken() {

            //prepare
            val oauthToken = igTokens.getOAuthToken()

            //validate
            Assertions.assertThat(oauthToken).isNotNull
        }
    }

    @Nested
    inner class `Use IGToken class to find streamToken`{

        @Test
        fun getStreamToken() {
            val result = igTokens.getStreamToken()

            //validate
            assertThat(result).isNotNull

        }
    }

    @Nested
    inner class `Use IGToken class to find lightStreamerEndpoint`{
        @Test
        fun getLightStreamerEndPoint() {

            //Execute
            val result = igTokens.getLightStreamerEndPoint()

            //validate
            assertThat(result).isNotBlank()
            assertThat(result).isNotEmpty()

        }
    }
}
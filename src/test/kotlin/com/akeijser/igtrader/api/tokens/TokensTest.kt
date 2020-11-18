package com.akeijser.igtrader.api.tokens

import com.akeijser.igtrader.api.testhelpers.OauthTokenFixture
import com.akeijser.igtrader.api.testhelpers.StreamTokenFixture
import com.akeijser.igtrader.configuration.ApplicationConfig
import io.mockk.*
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

internal class TokensTest {

    private val config : ApplicationConfig = mockk()
    private val tokens : Tokens = mockk()

    @BeforeEach
    fun setup(){
        every { config.ig.credentials.username } answers {"username"}
        every { config.ig.credentials.password } answers {"password"}
        every { config.ig.credentials.apiKey } answers {"apiKey"}
        every { config.ig.endpoints.session } answers {"http://igtraderendpoint/session"}
        every { config.ig.endpoints.prices } answers {"http://igtraderendpoint/prices"}
    }


    @Test
    fun getOAuthToken() {

        //setup
        every { tokens.getOAuthToken() } returns OauthTokenFixture.create()

        val oauthToken = tokens.getOAuthToken()

        //validate
        assertThat(oauthToken).isNotNull
        verify { tokens.getOAuthToken() }
    }

    @Test
    fun getStreamToken() {

        val streamToken = StreamTokenFixture.create()
        //setup
        every { tokens.getStreamToken() } returns streamToken

        val result = tokens.getStreamToken()

        //validate
        assertThat(result).isNotNull
        assertThat(result).isEqualTo(streamToken)
        verify {
            tokens.getStreamToken()
        }
    }

    @Test
    fun getLightStreamerEndPoint() {

        //setup
        val lightStreamerEndpoint = "http://someEndpoint.com"
        every { tokens.getLightStreamerEndPoint() } returns lightStreamerEndpoint

        val result = tokens.getLightStreamerEndPoint()

        //validate
        assertThat(result).isEqualTo(lightStreamerEndpoint)
        verify {
            tokens.getLightStreamerEndPoint()
        }
    }
}
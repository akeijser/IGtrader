package com.akeijser.igtrader.api.stream

import com.lightstreamer.client.LightstreamerClient
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class StreamServiceTest {

    private val streamService: StreamService = mockk()

    @Nested
    inner class `Test StreamService` {

        @Test
        fun `test getting a streamSession`() {
            //Prepare
            val session = LightstreamerClient("https://serveraddress.com", "adapter set")
            every { streamService.getStreamSession() } answers {session}


            //Execute
            val result = streamService.getStreamSession()

            //Assert
            assertThat(result).isEqualTo(session)

        }

    }
}
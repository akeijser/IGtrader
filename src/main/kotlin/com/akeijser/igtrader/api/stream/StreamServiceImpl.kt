package com.akeijser.igtrader.api.stream

import com.akeijser.igtrader.api.tokens.Tokens
import com.akeijser.igtrader.configuration.ApplicationConfig
import com.akeijser.igtrader.dto.StreamToken
import com.akeijser.igtrader.utils.IGSessionExceptions
import com.lightstreamer.client.ClientListener
import com.lightstreamer.client.LightstreamerClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StreamServiceImpl(private val config: ApplicationConfig, private val tokens: Tokens) : StreamService {

    private val LOGGER: Logger = LoggerFactory.getLogger(StreamServiceImpl::class.java)

    override fun getStreamSession() : LightstreamerClient{
        val streamToken : StreamToken
        val lsClient : LightstreamerClient

        try {
            streamToken = tokens.getStreamToken()
        } catch (e: IGSessionExceptions){
            LOGGER.info("Failed to get streamTokens. ${e.message}")
            throw e
        }

        lsClient = LightstreamerClient(streamToken.streamEndpoint, "DEMO")
        lsClient.connectionDetails?.user = config.ig.account.id
        lsClient.connectionDetails?.setPassword("CST-" + streamToken.cst + "|XST-" + streamToken.xSecurityToken)

        lsClient.addListener(SystemOutClientListener())
        lsClient.connect()

        return lsClient
    }

    override fun closeSession(){

    }


}

class SystemOutClientListener : ClientListener {

    override fun onListenEnd(client: LightstreamerClient) {
        println("Stops listening to client events")
    }

    override fun onListenStart(client: LightstreamerClient) {
        println("Start listening to client events")
    }

    override fun onPropertyChange(property: String) {
        println("Client property changed: $property")
    }

    override fun onServerError(code: Int, message: String) {
        println("Server error: $code: $message")
    }

    override fun onStatusChange(newStatus: String) {
        println("Connection status changed to $newStatus")
    }
}
package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.configuration.ApplicationConfig
import com.akeijser.igtrader.utils.IGSessionExceptions
import com.lightstreamer.client.LightstreamerClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object StreamSession {

    private val LOGGER: Logger = LoggerFactory.getLogger(StreamSession::class.java)
    private lateinit var lsClient : LightstreamerClient
    private lateinit var streamTokens: StreamTokens

    fun connect(config: ApplicationConfig): LightstreamerClient{

        try {
            streamTokens = RestSession.getStreamTokens(config)
        } catch (e: IGSessionExceptions){
            LOGGER.info("Failed to get streamTokens. ${e.message}")
        }

        lsClient = LightstreamerClient(streamTokens.streamEndpoint, "DEMO")
        lsClient.connectionDetails?.user = config.ig.account.id
        lsClient.connectionDetails?.setPassword("CST-" + streamTokens.cst + "|XST-" + streamTokens.xSecurityToken)

        lsClient.addListener(SystemOutClientListener())
        lsClient.connect()

        return lsClient

    }

    fun closeSession(){

    }

}
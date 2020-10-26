package com.akeijser.igtrader.igexternalapi

import com.lightstreamer.client.ClientListener
import com.lightstreamer.client.LightstreamerClient

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
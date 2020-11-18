package com.akeijser.igtrader.utils

import com.akeijser.igtrader.configuration.ApplicationConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


/**
 * This can be used if you need to inject through constuctor and therefor cant use a Kotlin object declaration to create a singleton
 */
@Component
class SingletonExample private constructor(private val config: ApplicationConfig) {

    companion object : SingletonHolder<SingletonExample, ApplicationConfig>(::SingletonExample)

    private val LOGGER: Logger = LoggerFactory.getLogger(SingletonExample::class.java)

    fun doSingletonStuff() {
        LOGGER.info(config.ig.account.id)
    }
}

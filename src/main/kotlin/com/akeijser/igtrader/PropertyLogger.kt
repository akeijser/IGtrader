package com.akeijser.igtrader

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.env.AbstractEnvironment
import org.springframework.core.env.EnumerablePropertySource
import org.springframework.core.env.Environment
import org.springframework.core.env.PropertySource
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.StreamSupport
import org.springframework.context.event.EventListener


@Component
class PropertyLogger {

    @EventListener
    fun handleContextRefresh(event: ContextRefreshedEvent) {
        val env: Environment = event.applicationContext.environment
        LOGGER.info("====== Environment and configuration ======")
        LOGGER.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()))
        val sources = (env as AbstractEnvironment).propertySources
        StreamSupport.stream(sources.spliterator(), false)
                .filter { ps: PropertySource<*>? -> ps is EnumerablePropertySource<*> }
                .map { ps: PropertySource<*> -> (ps as EnumerablePropertySource<*>).propertyNames }
                .flatMap<Any>(Arrays::stream)
                .distinct()
                .filter { prop -> !(prop.toString().contains("credentials") || prop.toString().contains("password"))}
                .forEach { prop -> LOGGER.info("{}: {}", prop, env.getProperty(prop.toString())) }
        LOGGER.info("===========================================")
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PropertyLogger::class.java)
    }
}
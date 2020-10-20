package com.akeijser.igtrader.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigConfiguration {

    @Bean("config")
    fun applicationConfig(): ApplicationConfig {
        return ApplicationConfig()
    }
}
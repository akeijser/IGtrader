package com.akeijser.igtrader.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@ConfigurationProperties(prefix = "application")
@Validated
@Component
@Order(value = 0 )
class ApplicationConfig {

    @Valid
    val ig = IgConfig()

    @ConfigurationProperties(prefix = "ig")
    class IgConfig{

        @Valid
        val endpoints = Endpoints()

        @ConfigurationProperties(prefix = "endpoints")
        class Endpoints{

            @NotBlank
            lateinit var session: String

            @NotBlank
            lateinit var marketNavigation: String

            @NotBlank
            lateinit var nodeNavigation: String

            @NotBlank
            lateinit var marketSearch: String

            @NotBlank
            lateinit var refreshToken: String

            @NotBlank
            lateinit var prices: String

            @NotBlank
            lateinit var streamToken: String
        }

        @Valid
        val credentials = IgCredentials()

        @ConfigurationProperties(prefix = "credentials")
        class IgCredentials{
            @NotBlank
            lateinit var apiKey: String

            @NotBlank
            lateinit var username: String

            @NotBlank
            lateinit var password: String
        }

        @Valid
        val account = AccountConfig()

        @ConfigurationProperties(prefix = "account")
        class AccountConfig{
            @NotBlank
            lateinit var id: String
        }
    }
}
package com.akeijser.igtrader

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.server.adapter.WebHttpHandlerBuilder.applicationContext


@SpringBootApplication
class IgtraderApplication{

	fun main(args: Array<String>) {
		runApplication<IgtraderApplication>(*args)
	}
}



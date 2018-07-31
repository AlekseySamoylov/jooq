package com.alekseysamoylov.jooq

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class JooqKotlinApplication

fun main(args: Array<String>) {
    SpringApplication.run(JooqKotlinApplication::class.java, *args)
}

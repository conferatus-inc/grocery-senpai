package org.example.mainbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class MainbackendApplication

fun main(args: Array<String>) {
    runApplication<MainbackendApplication>(*args)
}

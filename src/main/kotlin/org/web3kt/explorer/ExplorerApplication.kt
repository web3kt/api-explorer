package org.web3kt.explorer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ExplorerApplication

fun main(args: Array<String>) {
    runApplication<ExplorerApplication>(*args)
}

package org.web3kt.explorer.config

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3kt.core.Web3

@Configuration
class Web3Config {
    @Bean
    fun web3(): Web3 =
        Web3 {
            install(ContentNegotiation) {
                json(
                    Json {
                        encodeDefaults = true
                        isLenient = true
                        allowSpecialFloatingPointValues = true
                        allowStructuredMapKeys = true
                        prettyPrint = false
                        useArrayPolymorphism = false
                        ignoreUnknownKeys = true
                    },
                )
            }
            defaultRequest {
                url("https://testnet.directional.io")
            }
        }
}

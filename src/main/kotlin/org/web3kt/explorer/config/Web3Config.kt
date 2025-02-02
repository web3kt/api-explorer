package org.web3kt.explorer.config

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3kt.core.Web3
import org.web3kt.explorer.config.propertise.Web3Properties

@Configuration
class Web3Config(
    private val web3Properties: Web3Properties,
) {
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
                url(web3Properties.rpcEndpoint)
            }
        }
}

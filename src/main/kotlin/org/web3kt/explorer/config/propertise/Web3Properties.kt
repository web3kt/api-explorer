package org.web3kt.explorer.config.propertise

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "web3")
data class Web3Properties(
    val url: String,
)

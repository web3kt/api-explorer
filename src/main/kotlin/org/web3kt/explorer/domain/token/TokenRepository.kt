package org.web3kt.explorer.domain.token

import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token, String>

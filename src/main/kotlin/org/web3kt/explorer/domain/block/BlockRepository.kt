package org.web3kt.explorer.domain.block

import org.web3kt.explorer.domain.QJpaRepository
import java.math.BigInteger

interface BlockRepository : QJpaRepository<Block, BigInteger> {
    fun findFirstByOrderByIdDesc(): Block?
}

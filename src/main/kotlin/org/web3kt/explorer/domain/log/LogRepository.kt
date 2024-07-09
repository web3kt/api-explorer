package org.web3kt.explorer.domain.log

import org.springframework.data.jpa.repository.JpaRepository

interface LogRepository : JpaRepository<Log, Long>

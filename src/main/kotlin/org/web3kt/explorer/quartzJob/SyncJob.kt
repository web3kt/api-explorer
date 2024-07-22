package org.web3kt.explorer.quartzJob

import org.quartz.CronScheduleBuilder
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.JobExecutionContext
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import org.web3kt.explorer.service.SyncService

@Component
@DisallowConcurrentExecution
class SyncJob(
    private val syncService: SyncService,
) : QuartzJobBean() {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun executeInternal(context: JobExecutionContext) {
        val batchSize = 200.toBigInteger()

        val latestBlockNumber = syncService.latestBlockNumber()
        val maxBlockNumber = syncService.maxBlockNumber() ?: (-1).toBigInteger()
        val minBlockNumber = syncService.minBlockNumber()

        val (from, to) =
            if (latestBlockNumber > maxBlockNumber) {
                val to = latestBlockNumber
                val from =
                    if (latestBlockNumber - batchSize > maxBlockNumber + 1.toBigInteger()) {
                        latestBlockNumber - batchSize
                    } else {
                        maxBlockNumber + 1.toBigInteger()
                    }
                from to to
            } else if (minBlockNumber != null && minBlockNumber > 0.toBigInteger()) {
                val to = minBlockNumber - 1.toBigInteger()
                val from =
                    if (to - batchSize > 0.toBigInteger()) {
                        to - batchSize
                    } else {
                        0.toBigInteger()
                    }
                from to to
            } else {
                return
            }

        syncService.sync(from, to)
        logger.info("[$from ~ $to] synced")
    }

    @Bean
    fun syncJobDetail(): JobDetail =
        JobBuilder
            .newJob()
            .ofType(this::class.java)
            .storeDurably()
            .withIdentity(this::class.simpleName)
            .build()

    @Bean
    fun syncTrigger(syncJobDetail: JobDetail): Trigger =
        TriggerBuilder
            .newTrigger()
            .forJob(syncJobDetail)
            .withIdentity(this::class.simpleName)
            .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?"))
            .build()
}

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
        val latestBlockNumber = syncService.latestBlockNumber()
        val maxBlockNumber = syncService.maxBlockNumber()
        val minBlockNumber = syncService.minBlockNumber()

        val blockNumber = if (maxBlockNumber == null) {
            latestBlockNumber
        } else if (latestBlockNumber > maxBlockNumber) {
            maxBlockNumber + 1.toBigInteger()
        } else if (minBlockNumber != null && minBlockNumber > 0.toBigInteger()) {
            minBlockNumber - 1.toBigInteger()
        } else return

        syncService.sync(blockNumber)
        logger.info("#$blockNumber synced")
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

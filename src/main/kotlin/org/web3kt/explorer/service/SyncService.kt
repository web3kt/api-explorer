package org.web3kt.explorer.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.web3kt.core.Web3
import org.web3kt.core.protocol.dto.TransactionReceipt
import org.web3kt.core.protocol.dto.TransactionTrace
import org.web3kt.explorer.domain.block.Block
import org.web3kt.explorer.domain.block.BlockRepository
import org.web3kt.explorer.domain.internalTransaction.InternalTransaction
import org.web3kt.explorer.domain.internalTransaction.InternalTransactionRepository
import org.web3kt.explorer.domain.log.Log
import org.web3kt.explorer.domain.log.LogRepository
import org.web3kt.explorer.domain.tokenTransaction.TokenTransaction
import org.web3kt.explorer.domain.tokenTransaction.TokenTransactionRepository
import org.web3kt.explorer.domain.topic.Topic
import org.web3kt.explorer.domain.topic.TopicRepository
import org.web3kt.explorer.domain.transaction.Transaction
import org.web3kt.explorer.domain.transaction.TransactionRepository
import org.web3kt.explorer.internal.isContract
import java.math.BigInteger

@Service
class SyncService(
    private val web3: Web3,
    private val blockRepository: BlockRepository,
    private val transactionRepository: TransactionRepository,
    private val topicRepository: TopicRepository,
    private val logRepository: LogRepository,
    private val internalTransactionRepository: InternalTransactionRepository,
    private val tokenTransactionRepository: TokenTransactionRepository,
    private val tokenService: TokenService,
) {
    fun nextBlockNumber(): BigInteger = (blockRepository.findFirstByOrderByIdDesc()?.id ?: (-1).toBigInteger()) + 1.toBigInteger()

    fun latestBlockNumber(): BigInteger = runBlocking { web3.eth.blockNumber() }

    @Transactional
    fun sync(
        from: BigInteger,
        to: BigInteger,
    ): Unit =
        runBlocking {
            // retrieve blocks, transaction receipts, and transaction traces
            val blocks =
                (from.toLong()..to.toLong()).map { web3.eth.getBlock<org.web3kt.core.protocol.dto.Transaction>(it.toBigInteger()) }
            val transactionReceipts = blocks.flatMap { web3.eth.getBlockReceipts(it.number) }
            val transactionTraces =
                blocks.mapNotNull { if (it.transactions.isEmpty()) null else web3.trace.block(it.number) }.flatten()

            // save block and generate block map
            val blockMap =
                blocks
                    .map { it.toEntity() }
                    .run { blockRepository.saveAll(this) }
                    .associateBy { it.id }

            // generate transaction receipt map and save transactions
            val transactionReceiptMap =
                transactionReceipts
                    .associateBy { it.transactionHash }

            val transactionMap =
                blocks
                    .flatMap { it.transactions }
                    .map { it.toEntity(transactionReceiptMap[it.hash]!!, blockMap[it.blockNumber]!!) }
                    .run { transactionRepository.saveAll(this) }
                    .associateBy { it.id }

            // save logs
            val logs = transactionReceipts.flatMap { it.logs }

            val topicMap =
                logs
                    .flatMap { it.topics }
                    .map { topicRepository.findByValue(it) ?: topicRepository.save(Topic(it, null)) }
                    .associateBy { it.value }

            val tokenLogs =
                logs
                    .map { it.toEntity(transactionMap[it.transactionHash]!!, it.topics.map { topicMap[it]!! }) }
                    .run { logRepository.saveAll(this) }
                    .filter { it.topics.firstOrNull()?.value == "0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef" }

            // save token transactions
            val tokenMap = tokenLogs.associate { it.address to tokenService.findById(it.address) }

            tokenLogs
                .map {
                    TokenTransaction(
                        id = it.id!!,
                        log = it,
                        timestamp = it.timestamp,
                        token = tokenMap[it.address]!!,
                        from = "0x" + it.topics[1].value.takeLast(40),
                        to = "0x" + it.topics[2].value.takeLast(40),
                        value = BigInteger(it.data.removePrefix("0x"), 16),
                    )
                }.run { tokenTransactionRepository.saveAll(this) }

            // save internal transactions
            transactionTraces
                .filter { it.action.from?.let { runBlocking { web3.isContract(it) } } == true }
                .mapNotNull { it.transactionHash?.let { hash -> it.toEntity(transactionMap[hash]!!) } }
                .run { internalTransactionRepository.saveAll(this) }
        }

    fun org.web3kt.core.protocol.dto.Block<org.web3kt.core.protocol.dto.Transaction>.toEntity(): Block =
        Block(
            id = number,
            baseFeePerGas = baseFeePerGas,
            blobGasUsed = blobGasUsed,
            difficulty = difficulty,
            excessBlobGas = excessBlobGas,
            extraData = extraData,
            gasLimit = gasLimit,
            gasUsed = gasUsed,
            hash = hash,
            logsBloom = logsBloom,
            miner = miner,
            mixHash = mixHash,
            nonce = nonce,
            parentBeaconBlockRoot = parentBeaconBlockRoot,
            parentHash = parentHash,
            receiptsRoot = receiptsRoot,
            sha3Uncles = sha3Uncles,
            size = size,
            stateRoot = stateRoot,
            timestamp = timestamp.toLong() * 1000,
            totalDifficulty = totalDifficulty,
            transactionCount = transactions.size,
            transactionsRoot = transactionsRoot,
        )

    fun org.web3kt.core.protocol.dto.Transaction.toEntity(
        transactionReceipt: TransactionReceipt,
        block: Block,
    ): Transaction =
        Transaction(
            id = hash,
            block = block,
            timestamp = block.timestamp,
            contractAddress = transactionReceipt.contractAddress,
            cumulativeGasUsed = transactionReceipt.cumulativeGasUsed,
            from = from,
            gasUsed = transactionReceipt.gasUsed,
            effectiveGasPrice = transactionReceipt.effectiveGasPrice,
            logsBloom = transactionReceipt.logsBloom,
            status = transactionReceipt.status,
            revertReason = transactionReceipt.revertReason,
            to = to ?: "0x0000000000000000000000000000000000000000",
            transactionIndex = transactionIndex,
            type = transactionReceipt.type,
            chainId = chainId,
            gas = gas,
            gasPrice = gasPrice,
            input = input,
            maxFeePerGas = maxFeePerGas,
            maxPriorityFeePerGas = maxPriorityFeePerGas,
            nonce = nonce,
            r = r,
            s = s,
            v = v,
            value = value,
            yParity = yParity,
        )

    fun org.web3kt.core.protocol.dto.Log.toEntity(
        transaction: Transaction,
        topics: List<Topic>,
    ): Log =
        Log(
            timestamp = transaction.timestamp,
            logIndex = logIndex,
            removed = removed,
            transaction = transaction,
            address = address,
            data = data,
            topics = topics,
        )

    fun TransactionTrace.toEntity(transaction: Transaction): InternalTransaction =
        InternalTransaction(
            transaction = transaction,
            timestamp = transaction.timestamp,
            callType = action.callType,
            from = action.from ?: "0x0000000000000000000000000000000000000000",
            gas = action.gas,
            input = action.input,
            to = action.to ?: "0x0000000000000000000000000000000000000000",
            value = action.value,
            author = action.author,
            rewardType = action.rewardType,
            gasUsed = result?.gasUsed,
            output = result?.output,
            type = type,
            subTraces = subtraces,
        )
}

package org.web3kt.explorer.internal

import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle

class PrettyFormat : MessageFormattingStrategy {
    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?,
    ): String = "[connectionId=$connectionId] $url: $category completed in $elapsed ms ${formatSql(sql)}"

    private fun formatSql(sql: String?): String? {
        if (sql.isNullOrBlank()) return sql
        val formattedSQl =
            if (PREFIXES.any { sql.startsWith(it) }) {
                FormatStyle.BASIC.formatter.format(sql)
            } else {
                FormatStyle.DDL.formatter.format(sql)
            }.let { FormatStyle.HIGHLIGHT.formatter.format(it) }
        return "\nP6Spy:$formattedSQl"
    }

    companion object {
        private val PREFIXES = listOf("select", "insert", "update", "delete")
    }
}

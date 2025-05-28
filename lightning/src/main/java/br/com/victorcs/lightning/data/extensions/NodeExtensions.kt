package br.com.victorcs.lightning.data.extensions

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_FORMAT = "dd/MM/yyyy HH:mm"
const val HYPHEN = "-"
private const val BITCOIN_TRANSFORM = 100_000_000.0
private const val ONE_THOUSAND = 1000
private const val ERROR_CONVERTER_DATE =  "Error formatting date from timestamp:"
private const val ERROR_CONVERTER_BITCOIN =  "Error converting to Bitcoin from value:"

fun Long.toFormattedDate(): String {
    return try {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        sdf.format(Date(this * ONE_THOUSAND))
    } catch (e: Exception) {
        Timber.e(e, "$ERROR_CONVERTER_DATE $this")
        HYPHEN
    }
}

fun Long.toBitcoin(): Double {
    return try {
        this.toDouble() / BITCOIN_TRANSFORM
    } catch (e: Exception) {
        Timber.e(e, "$ERROR_CONVERTER_BITCOIN $this")
        0.0
    }
}

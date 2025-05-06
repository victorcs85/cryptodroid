package br.com.victorcs.cryptodroid.infrastructure.source.local.repository

import android.content.Context
import br.com.victorcs.core.constants.EMPTY
import br.com.victorcs.core.constants.UTF_8
import timber.log.Timber
import java.io.IOException
import java.io.InputStream

class ExchangeLocalProvider(private val context: Context) : IExchangeLocalProvider {

    override fun loadJSONFile(file: ExchangeLocalProviderType) = run {
        var json = EMPTY
        try {
            val inputStream: InputStream = context.assets.open(file.fileName)
            with(inputStream) {
                val size = available()
                val byteArray = ByteArray(size)
                read(byteArray)
                close()
                json = String(byteArray, charset(UTF_8))
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
        json
    }
}

enum class ExchangeLocalProviderType(val fileName: String) {
    EXCHANGES("response_200_exchanges.json"),
    ICONS("response_200_icons.json"),
}

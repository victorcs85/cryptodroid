package br.com.victorcs.cryptodroid.core.exceptions

import br.com.victorcs.cryptodroid.core.constants.NETWORK_MESSAGE_ERROR
import java.io.IOException

class WithoutNetworkException: IOException(NETWORK_MESSAGE_ERROR)

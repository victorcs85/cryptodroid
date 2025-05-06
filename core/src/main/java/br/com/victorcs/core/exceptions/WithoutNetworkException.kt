package br.com.victorcs.core.exceptions

import br.com.victorcs.core.constants.NETWORK_MESSAGE_ERROR
import java.io.IOException

class WithoutNetworkException : IOException(NETWORK_MESSAGE_ERROR)

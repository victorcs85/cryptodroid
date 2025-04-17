package br.com.victorcs.cryptodroid.core.exceptions

import br.com.victorcs.cryptodroid.core.constants.TOO_MANY_REQUESTS_MESSAGE_ERROR
import java.io.IOException

class RateLimitException: IOException(TOO_MANY_REQUESTS_MESSAGE_ERROR)
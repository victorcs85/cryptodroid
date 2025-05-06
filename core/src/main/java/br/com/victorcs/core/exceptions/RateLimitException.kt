package br.com.victorcs.core.exceptions

import br.com.victorcs.core.constants.TOO_MANY_REQUESTS_MESSAGE_ERROR
import java.io.IOException

class RateLimitException : IOException(TOO_MANY_REQUESTS_MESSAGE_ERROR)

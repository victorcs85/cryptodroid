package br.com.victorcs.lightning.domain.model

data class Country(
    val de: String?,
    val en: String?,
    val es: String?,
    val fr: String?,
    val ja: String?,
    val ptBR: String?,
    val ru: String?,
    val zhCN: String?
) {
    fun getLocalizedName(): String {
        return ptBR?.takeIf { it.isNotBlank() } ?: en.orEmpty()
    }
}

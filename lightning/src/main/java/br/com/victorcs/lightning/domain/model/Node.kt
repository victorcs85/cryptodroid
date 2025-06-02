package br.com.victorcs.lightning.domain.model

data class Node(
    val publicKey: String,
    val alias: String?,
    val channels: Int?,
    val capacity: Double?,
    val firstSeen: String?,
    val updatedAt: String?,
    val city: City?,
    val country: Country?,
    val isoCode: String?,
    val subdivision: String?,
)

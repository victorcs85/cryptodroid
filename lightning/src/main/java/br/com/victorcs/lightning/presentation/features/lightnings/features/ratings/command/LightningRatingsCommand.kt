package br.com.victorcs.lightning.presentation.features.lightnings.features.ratings.command

sealed class LightningRatingsCommand {
    object GetRatings : LightningRatingsCommand()
}

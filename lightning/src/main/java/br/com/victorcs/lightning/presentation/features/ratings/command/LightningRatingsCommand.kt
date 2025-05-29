package br.com.victorcs.lightning.presentation.features.ratings.command

sealed class LightningRatingsCommand {
    object GetRatings : LightningRatingsCommand()
}

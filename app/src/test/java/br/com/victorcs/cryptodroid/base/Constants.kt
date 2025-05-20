package br.com.victorcs.cryptodroid.base

import android.os.Build

const val SDK = Build.VERSION_CODES.TIRAMISU
const val SCREEN_SIZE = "w411dp-h731dp"
const val INSTRUMENTED_PACKAGE = "androidx.loader.content"

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ComposeUiTest
package org.cmpai.project

import androidx.compose.ui.window.ComposeUIViewController
import org.cmpai.project.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
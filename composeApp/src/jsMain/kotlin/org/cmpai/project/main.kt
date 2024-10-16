package org.cmpai.project
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    Firebase.initialize(
        options = FirebaseOptions(
            applicationId = "1:329551685826:web:7bc79445d2da8df4d8b35b",
            apiKey = "AIzaSyC-9HLCd13eNXwGqvycb2jFdgfQWVr8MJ0",
            projectId = "cmpai-57733"
        )
    )

    onWasmReady {
        CanvasBasedWindow(canvasElementId = "ComposeTarget") {
            App()
        }
    }
}

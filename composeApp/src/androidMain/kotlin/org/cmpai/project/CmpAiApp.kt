package org.cmpai.project

import android.app.Application
import org.cmpai.project.di.initKoin

class CmpAiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
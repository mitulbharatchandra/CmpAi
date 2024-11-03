package org.cmpai.project.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.cmpai.project.auth.AuthService
import org.cmpai.project.auth.AuthServiceImpl
import org.cmpai.project.auth.LoginViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single {
        AuthServiceImpl(Firebase.auth)
    }.bind<AuthService>()
    viewModelOf(::LoginViewModel)
}

fun initKoin() {
    startKoin {
        loadKoinModules(sharedModule)
    }
}
package com.example.sma

import android.app.Application
import com.example.sma.data.AppContainer
import com.example.sma.data.DefaultAppContainer

/**
 * Application principal que configura o AppContainer no arranque.
 */
class CineLogApplication : Application() {
    /**
     * Instância do AppContainer partilhada por todas as partes da aplicação.
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}

package ru.uspensky.revolut

import android.app.Application
import ru.uspensky.revolut.di.DaggerWrapper

/**
 * Created by Dmitry Uspensky on 23/10/2018.
 */
class RevolutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DaggerWrapper.getComponentManager(this)
    }
}
package ru.uspensky.revolut.di.component

import android.content.Context
import dagger.Component
import io.reactivex.subjects.PublishSubject
import ru.uspensky.revolut.data.network.ConnectivityStatusReceiver
import ru.uspensky.revolut.data.network.NetworkFunctions
import ru.uspensky.revolut.di.module.AppModule
import ru.uspensky.revolut.domain.interactor.Ticker
import javax.inject.Singleton

/**
 * Created by Dmitry Uspensky on 23/10/2018.
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun context(): Context
    fun subject(): PublishSubject<Pair<Ticker, Double>>
    fun connectivityStatusReceiver(): ConnectivityStatusReceiver
    fun networkFunctions(): NetworkFunctions
}
package ru.uspensky.revolut.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import ru.uspensky.revolut.data.network.ConnectivityStatusReceiver
import ru.uspensky.revolut.data.network.NetworkFunctions
import ru.uspensky.revolut.domain.interactor.Ticker
import javax.inject.Singleton

/**
 * Created by Dmitry Uspensky on 23/10/2018.
 */

@Module
class AppModule constructor(val context: Context) {
    @Singleton
    @Provides
    fun provideAppContext(): Context = context

    @Singleton
    @Provides
    fun providesPublishSubject(): PublishSubject<Pair<Ticker, Double>> = PublishSubject.create<Pair<Ticker, Double>>()

    @Singleton
    @Provides
    fun provideConnectivityStatusReceiver(): ConnectivityStatusReceiver = ConnectivityStatusReceiver()

    @Singleton
    @Provides
    fun provideNetworkFunctions(context: Context): NetworkFunctions = NetworkFunctions(context)
}
package ru.uspensky.revolut.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import ru.uspensky.revolut.data.network.NetworkFunctions
import ru.uspensky.revolut.data.repository.RatesRepository
import ru.uspensky.revolut.data.storage.RatesStorage
import ru.uspensky.revolut.domain.interactor.RatesInteractor
import ru.uspensky.revolut.domain.interactor.Ticker
import ru.uspensky.revolut.presentation.presenter.RatesPresenter
import javax.inject.Singleton

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */

@Module
class RatesModule {
    @Provides
    fun providesStorage(): RatesStorage = RatesStorage()

    @Provides
    fun providesRepository(storage: RatesStorage): RatesRepository = RatesRepository(storage)

    @Provides
    fun providesInteractor(repository: RatesRepository): RatesInteractor = RatesInteractor(repository)

    @Provides
    fun providesPresenter(interactor: RatesInteractor, subject: PublishSubject<Pair<Ticker, Double>>, networkFunctions: NetworkFunctions): RatesPresenter = RatesPresenter(interactor, subject, networkFunctions)
}
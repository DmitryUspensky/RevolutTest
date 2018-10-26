package ru.uspensky.revolut.data.repository

import io.reactivex.Observable
import ru.uspensky.revolut.data.network.response.RatesResponse
import ru.uspensky.revolut.data.storage.RatesStorage
import ru.uspensky.revolut.domain.interactor.Ticker
import ru.uspensky.revolut.domain.repository.IRatesRepository
import javax.inject.Inject

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
class RatesRepository @Inject constructor(private val ratesStorage: RatesStorage): IRatesRepository {
    override fun getRates(ticker: Ticker): Observable<RatesResponse> {
        return ratesStorage.getRates(ticker)
    }
}
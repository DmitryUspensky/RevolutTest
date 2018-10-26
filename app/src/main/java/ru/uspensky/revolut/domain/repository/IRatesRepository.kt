package ru.uspensky.revolut.domain.repository

import io.reactivex.Observable
import ru.uspensky.revolut.data.network.response.RatesResponse
import ru.uspensky.revolut.domain.interactor.Ticker

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
interface IRatesRepository {
    fun getRates(ticker: Ticker): Observable<RatesResponse>
}
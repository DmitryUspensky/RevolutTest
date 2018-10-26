package ru.uspensky.revolut.domain.interactor

import io.reactivex.Observable
import ru.uspensky.revolut.data.network.response.RatesResponse
import ru.uspensky.revolut.data.network.response.multiple
import ru.uspensky.revolut.domain.repository.IRatesRepository
import javax.inject.Inject

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
class RatesInteractor @Inject constructor(private val ratesRepository: IRatesRepository) {
    fun getRates(ticker: Ticker, count: Double): Observable<Pair<RatesResponse, Double>> {
        return ratesRepository
                .getRates(ticker)
                .flatMap { response ->
                    response.rates.multiple(count)
                    Observable.just(Pair(response, count))
                }
    }
}
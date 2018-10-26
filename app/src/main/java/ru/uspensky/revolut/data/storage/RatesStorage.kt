package ru.uspensky.revolut.data.storage

import io.reactivex.Observable
import ru.uspensky.revolut.data.network.response.RatesResponse
import ru.uspensky.revolut.data.network.submitter.RatesSubmitter
import ru.uspensky.revolut.domain.interactor.Ticker

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
class RatesStorage {
    fun getRates(baseTicker: Ticker): Observable<RatesResponse> {
        return RatesSubmitter(baseTicker).observable
    }
}
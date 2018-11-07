package ru.uspensky.revolut.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.uspensky.revolut.data.network.ConnectivityStatusReceiver
import ru.uspensky.revolut.data.network.NetworkFunctions
import ru.uspensky.revolut.data.network.response.Rates
import ru.uspensky.revolut.domain.interactor.RatesInteractor
import ru.uspensky.revolut.domain.interactor.Ticker
import ru.uspensky.revolut.presentation.view.IRatesView
import ru.uspensky.revolut.presentation.view.RatesAdapter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */

@InjectViewState
class RatesPresenter @Inject constructor(
        private val interactor: RatesInteractor,
        private val subject: PublishSubject<Pair<Ticker, Double>>,
        private val networkFunctions: NetworkFunctions)
    : IRatesPresenter, MvpPresenter<IRatesView>(), ConnectivityStatusReceiver.IConnectivityCallback {

    private var lastCount: Double = 100.0
    private var lastTicker = Ticker.EUR

    private var disposableTimer: Disposable? = null

    init {
        val isNotworkConnected = networkFunctions.isNetworkConnected()
        if (isNotworkConnected) {
            val defaultBase = Ticker.EUR
            interactor.getRates(defaultBase, 100.0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        viewState.showRates(getDataForAdapter(result.first.rates, defaultBase, 100.0), defaultBase, true)
                    }
            registerTimer()
        }
        registerSubject()
        viewState.showEmptyMessage(!isNotworkConnected)
    }

    override fun onRatesSelected(ticker: Ticker, count: Double) {
        interactor.getRates(ticker, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ result ->
                    disposableTimer?.dispose()
                    viewState.showRates(getDataForAdapter(result.first.rates, ticker, count), ticker, true)
                    registerTimer()
                },{ viewState.showNetworkError() })
    }

    override fun onConnectivityStateChanged(connectPresent: Boolean) {
        if (connectPresent) {
            disposableTimer?.dispose()
            registerTimer()
        } else {
            viewState.showEmptyMessage(true)
        }
    }

    private fun registerSubject() {
        subject.debounce(250, TimeUnit.MILLISECONDS)
                .switchMap {
                    pair -> interactor.getRates(pair.first, pair.second)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ result ->
                    disposableTimer?.dispose()
                    val ticker = Ticker.valueOf(result.first.base)
                    if (ticker == lastTicker) {
                        viewState.showRates(getDataForAdapter(result.first.rates, ticker, result.second), ticker, true)
                    }
                    registerTimer()
                }, {viewState.showNetworkError()})
    }

    private fun registerTimer() {
        disposableTimer = interactor.getRates(lastTicker, lastCount)
                .repeatWhen { repeatHandler ->
                    repeatHandler.delay(1, TimeUnit.SECONDS)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ result ->
                    val ticker = Ticker.valueOf(result.first.base)
                    viewState.showRates(getDataForAdapter(result.first.rates, ticker, result.second), ticker, false)
                    viewState.showEmptyMessage(false)
                })
                {viewState.showNetworkError()}
    }

    private fun getDataForAdapter(rates: Rates, base: Ticker, count: Double): List<RatesAdapter.ItemData> {
        lastCount = count
        lastTicker = base
        val tickers = Ticker.values().toMutableList()
        tickers.remove(base)
        tickers.sortWith(Comparator { p1, p2 ->
            p1.name.compareTo(p2.name)
        })
        tickers.add(0, base)
        return ArrayList<RatesAdapter.ItemData>().apply {
            tickers.forEach { ticker: Ticker ->
                val priceForTicker = if (ticker == base) count else getPriceForTicker(rates, ticker)
                add(RatesAdapter.ItemData(ticker == base, ticker, priceForTicker))
            }
        }
    }

    private fun getPriceForTicker(rates: Rates, ticker: Ticker): Double {
        return when (ticker) {
            Ticker.AUD -> rates.aud
            Ticker.BGN -> rates.bgn
            Ticker.BRL -> rates.brl
            Ticker.CAD -> rates.cad
            Ticker.CHF -> rates.chf
            Ticker.CNY -> rates.cny
            Ticker.CZK -> rates.czk
            Ticker.DKK -> rates.dkk
            Ticker.EUR -> rates.eur
            Ticker.GBP -> rates.gbp
            Ticker.HKD -> rates.hkd
            Ticker.HRK -> rates.hrk
            Ticker.HUF -> rates.huf
            Ticker.IDR -> rates.idr
            Ticker.ILS -> rates.ils
            Ticker.INR -> rates.inr
            Ticker.ISK -> rates.isk
            Ticker.JPY -> rates.jpy
            Ticker.KRW -> rates.krw
            Ticker.MXN -> rates.mxn
            Ticker.MYR -> rates.myr
            Ticker.NOK -> rates.nok
            Ticker.NZD -> rates.nzd
            Ticker.PHP -> rates.php
            Ticker.PLN -> rates.pln
            Ticker.RON -> rates.ron
            Ticker.RUB -> rates.rub
            Ticker.SEK -> rates.sek
            Ticker.SGD -> rates.sgd
            Ticker.THB -> rates.thb
            Ticker.TRY -> rates.tryy
            Ticker.USD -> rates.usd
            Ticker.ZAR -> rates.zar
        }
    }
}
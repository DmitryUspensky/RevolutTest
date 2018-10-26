package ru.uspensky.revolut.presentation.presenter

import ru.uspensky.revolut.domain.interactor.Ticker

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
interface IRatesPresenter {
    fun onRatesSelected(ticker: Ticker, count: Double)
}
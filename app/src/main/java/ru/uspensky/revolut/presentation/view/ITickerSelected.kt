package ru.uspensky.revolut.presentation.view

import ru.uspensky.revolut.domain.interactor.Ticker

/**
 * Created by Dmitry Uspensky on 23/10/2018.
 */
interface ITickerSelected {
    fun onSelected(ticker: Ticker, count: Double)
}
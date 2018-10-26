package ru.uspensky.revolut.presentation.view

import com.arellomobile.mvp.MvpView
import ru.uspensky.revolut.data.network.response.Rates
import ru.uspensky.revolut.domain.interactor.Ticker

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
interface IRatesView : MvpView {
    fun showRates(data: List<RatesAdapter.ItemData>, ticker: Ticker, onDemand: Boolean)
    fun showNetworkError()
    fun showEmptyMessage(show: Boolean)
}
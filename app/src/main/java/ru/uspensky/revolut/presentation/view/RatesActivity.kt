package ru.uspensky.revolut.presentation.view

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.uspensky.revolut.R
import ru.uspensky.revolut.di.DaggerWrapper
import ru.uspensky.revolut.presentation.presenter.RatesPresenter
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import ru.uspensky.revolut.domain.interactor.Ticker
import io.reactivex.subjects.PublishSubject
import ru.uspensky.revolut.data.network.ConnectivityStatusReceiver

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
class RatesActivity : MvpAppCompatActivity(), IRatesView, ITickerSelected {

    private var ratesAdapter: RatesAdapter? = null
    private var ratesList: RecyclerView? = null
    private var coordinatorLayout: CoordinatorLayout? = null
    private var emptyMessage: TextView? = null

    init {
        initializeInjector()
    }

    @Inject
    lateinit var subject: PublishSubject<Pair<Ticker, Double>>

    @Inject
    @InjectPresenter
    lateinit var presenter: RatesPresenter

    @Inject
    lateinit var connectivityStatusReceiver: ConnectivityStatusReceiver

    @ProvidePresenter
    fun providePresenter(): RatesPresenter {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ratesList = findViewById(R.id.ratesList)
        coordinatorLayout = findViewById(R.id.coord)
        emptyMessage = findViewById(R.id.emptyMessage)
        ratesList?.layoutManager = LinearLayoutManager(this)

        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityStatusReceiver, filter)
        connectivityStatusReceiver.registerListener(presenter)
    }

    override fun showRates(data: List<RatesAdapter.ItemData>, ticker: Ticker, onDemand: Boolean) {
        if (ratesAdapter == null) {
            ratesAdapter = RatesAdapter(this, data, this, subject)
            val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
            ratesList?.addItemDecoration(dividerItemDecoration)
            ratesList?.adapter = ratesAdapter
            (ratesList?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        } else {
            ratesAdapter?.onNewDataArrived(data)
        }
        if (onDemand) {
            ratesList?.scrollToPosition(0)
        }
    }

    override fun showNetworkError() {
        coordinatorLayout?.let {
            Snackbar.make(it, "Network error", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showEmptyMessage(show: Boolean) {
        emptyMessage?.visibility = if (show) View.VISIBLE else View.GONE
        ratesList?.visibility = if (show) View.GONE else View.VISIBLE
        if (show) {
            hideKeyboard()
        }
    }

    override fun onSelected(ticker: Ticker, count: Double) {
        presenter.onRatesSelected(ticker, count)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityStatusReceiver.unRegisterListener(presenter)
        clearInjector()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun initializeInjector() {
        DaggerWrapper.getComponentManager()?.getRatesComponent()?.inject(this)
    }

    private fun clearInjector() {
        DaggerWrapper.getComponentManager()?.clearRatesComponent()
    }
}
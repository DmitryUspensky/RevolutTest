package ru.uspensky.revolut.presentation.view

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.Editable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.uspensky.revolut.R
import ru.uspensky.revolut.domain.interactor.Ticker
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import io.reactivex.subjects.PublishSubject


/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
class RatesAdapter(
        private val context: Context,
        private var data: List<ItemData>,
        private val callback: ITickerSelected,
        private val subject:  PublishSubject<Pair<Ticker, Double>>)
    : RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    private val formatter: NumberFormat

    init {
        formatter = NumberFormat.getInstance(Locale.FRANCE) as DecimalFormat
        val symbols = formatter.decimalFormatSymbols
        symbols.decimalSeparator = '.'
        formatter.decimalFormatSymbols = symbols
        formatter.maximumFractionDigits = 2
        formatter.roundingMode = RoundingMode.DOWN
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rate, parent, false)
        val holder = ViewHolder(view, callback, subject)
        holder.root?.setOnClickListener {
            callback.onSelected(Ticker.values()[viewType], parseString(holder.price?.text.toString()))
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type.ordinal
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    fun onNewDataArrived(newData: List<ItemData>) {
        val result = DiffUtil.calculateDiff(AdapterDiffUtil(data, newData))
        data = newData
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(
            itemView: View?,
            private val callback: ITickerSelected,
            private val subject:  PublishSubject<Pair<Ticker, Double>>
    ) : RecyclerView.ViewHolder(itemView) {
        val root = itemView?.findViewById<View>(R.id.root)
        val name = itemView?.findViewById<TextView>(R.id.name)
        val description = itemView?.findViewById<TextView>(R.id.description)
        val price = itemView?.findViewById<CustomEditText>(R.id.price)

        fun onBind(itemData: ItemData) {
            name?.text = itemData.type.name
            description?.text = itemData.type.description
            price?.clearTextChangedListeners()
            price?.onFocusChangeListener = null
            price?.setText(formatter.format(itemData.price))
            if (itemData.isSelected) {
                price?.requestFocus()
            }
            price?.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    callback.onSelected(itemData.type, parseString(price.text.toString()))
                } else if (price.text.isEmpty()) {
                    price.setText("0", TextView.BufferType.EDITABLE)
                }
            }
            if (itemData.isSelected) {
                price?.addTextChangedListener(object : CustomTextWatcher() {
                    override fun afterTextChanged(p0: Editable?) {
                        subject.onNext(Pair(itemData.type, parseString(price.text.toString())))
                    }
                })
            }
        }
    }

    data class ItemData (
        val isSelected: Boolean = false,
        val type: Ticker = Ticker.EUR,
        val price: Double = 0.0
    )

    private fun parseString(priceStr: String): Double {
        return if (priceStr.isNotEmpty() && priceStr != ".") {
            formatter.parse(priceStr).toDouble()
        } else {
            0.0
        }
    }
}
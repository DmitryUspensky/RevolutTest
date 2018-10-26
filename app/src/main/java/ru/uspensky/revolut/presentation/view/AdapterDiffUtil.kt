package ru.uspensky.revolut.presentation.view

import android.support.v7.util.DiffUtil

/**
 * Created by Dmitry Uspensky on 23/10/2018.
 */
class AdapterDiffUtil(
        private val oldList: List<RatesAdapter.ItemData>,
        private val newList: List<RatesAdapter.ItemData>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].type == newList[newItemPosition].type
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return (oldItem.price == newItem.price) || oldItem.isSelected
    }
}
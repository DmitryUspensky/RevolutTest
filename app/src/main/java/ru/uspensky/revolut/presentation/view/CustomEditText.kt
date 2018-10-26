package ru.uspensky.revolut.presentation.view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText

/**
 * Created by Dmitry Uspensky on 24/10/2018.
 */
class CustomEditText : EditText {

    private var mListeners: ArrayList<TextWatcher>? = null

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    constructor(ctx: Context, attrs: AttributeSet, defStyle: Int) : super(ctx, attrs, defStyle)

    override fun addTextChangedListener(watcher: TextWatcher) {
        if (mListeners == null) {
            mListeners = ArrayList()
        }
        mListeners!!.add(watcher)

        super.addTextChangedListener(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher) {
        if (mListeners != null) {
            val i = mListeners!!.indexOf(watcher)
            if (i >= 0) {
                mListeners!!.removeAt(i)
            }
        }

        super.removeTextChangedListener(watcher)
    }

    fun clearTextChangedListeners() {
        if (mListeners != null) {
            for (watcher in mListeners!!) {
                super.removeTextChangedListener(watcher)
            }

            mListeners!!.clear()
            mListeners = null
        }
    }
}
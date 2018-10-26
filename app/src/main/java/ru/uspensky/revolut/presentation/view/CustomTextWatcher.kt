package ru.uspensky.revolut.presentation.view

import android.text.TextWatcher

/**
 * Created by Dmitry Uspensky on 23/10/2018.
 */
abstract class CustomTextWatcher: TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Not important
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Not important
    }
}
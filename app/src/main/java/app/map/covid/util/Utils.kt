package app.map.covid.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {

    fun View.hideKeyboard(): Boolean {
        try {
            clearFocus()
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }
}
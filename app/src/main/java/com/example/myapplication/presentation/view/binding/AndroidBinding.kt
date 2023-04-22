package com.example.myapplication.presentation.view.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * @author Raphael Fersan
 */
internal object AndroidBinding {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun View.setVisibility(value: Boolean) {
        visibility = if (value) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

package com.rba.thegitusers.common

import android.databinding.BindingAdapter
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rba.thegitusers.R
import java.text.ParseException

/**
 * [BindingAdapter] to extract the image from the provided [url] source and
 * inflate the image in the [ImageView] if the provided [url] is valid.
 */
@BindingAdapter("app:srcUrl")
fun ImageView.setUrlSource(url: String) {

    fun imagePlaceholderResource(): Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            android.R.drawable.ic_menu_recent_history
        } else {
            R.drawable.image_load_placeholder
        }
    }

    fun imageFailureResource(): Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            android.R.drawable.ic_delete
        } else {
            R.drawable.image_load_failure
        }
    }


    // Making sure that the provided URL is valid before loading the image.
    if (url.isValidUrl()) {
        Glide.with(context).load(url).placeholder(imagePlaceholderResource()).into(this)
    } else {
        Glide.with(context).load(imageFailureResource()).into(this)
    }
}

/**
 * [BindingAdapter] to format the provided [date] String in [UTC_DATE_FORMAT] to [UI_DATE_FORMAT],
 * and set it to the [TextView].
 */
@BindingAdapter("app:textDate")
fun TextView.setTextDate(date: String) {
    // Converting the desired date to required UI format, and setting the
    // date to the TextView.
    text = try {
        getDesiredDateInDesiredFormat(date, UTC_DATE_FORMAT, UI_DATE_FORMAT)
    } catch (e: ParseException) {
        date
    }
}
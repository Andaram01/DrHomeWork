package com.dr.drhomework.util.extension

import android.text.Html
import android.widget.TextView
import com.dr.drhomework.util.GlideImageGetter


fun TextView.setTextHtml(source: String?) {
    if (!source.isNullOrEmpty()) {
        text = Html.fromHtml(source, GlideImageGetter(context, this), null)
    }
}



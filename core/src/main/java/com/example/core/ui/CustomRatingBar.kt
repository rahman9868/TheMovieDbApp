package com.example.core.ui

import android.R
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView


class CustomRatingBar : LinearLayout {
    private var ratingBar: RatingBar? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = HORIZONTAL
        ratingBar = RatingBar(context, attrs, R.attr.ratingBarStyleSmall)
        ratingBar!!.max = 5 // Set the maximum number of stars
        ratingBar!!.stepSize = 0.5f // Set the step size to achieve half-stars
        addView(ratingBar)

        // Optional: Set a default rating or customize the appearance further
        rating = 0.0f
    }

    var rating: Float
        get() = ratingBar!!.rating
        set(rating) {
            ratingBar!!.rating = rating / 2
        }
}

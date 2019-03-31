package com.rba.thegitusers.features

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rba.thegitusers.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun toggleLoadingIconVisibility(isLoading: Boolean) {
        pbLoading?.run { visibility = if (isLoading) View.VISIBLE else View.GONE }
    }
}

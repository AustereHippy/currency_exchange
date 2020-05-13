package com.test.currencyexchange.ui.global

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.currencyexchange.R

class ApplicationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_layout_container)
    }
}

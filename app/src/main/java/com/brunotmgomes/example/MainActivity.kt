package com.brunotmgomes.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brunotmgomes.R
import com.brunotmgomes.keyboarddelegate.KeyboardDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KeyboardDelegate.initialize(this)
    }
}

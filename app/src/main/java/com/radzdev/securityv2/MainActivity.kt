package com.radzdev.securityv2

import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Security check
        SecurityChecker.validate(
            this,
            "6924268D77DA70BD33C139F5D0345DA455EE9C61",
            "securityv2",
            "com.radzdev.securityv2"
        )
    }
}

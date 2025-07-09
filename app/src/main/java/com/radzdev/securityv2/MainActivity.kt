package com.radzdev.securityv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.radzdev.securityv2.ui.theme.Securityv2Theme
import com.radzdev.security.SecurityChecker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Security check
        SecurityChecker.validate(
            this,
            "69:24:26:8D:77:DA:70:BD:33:C1:39:F5:D0:34:5D:A4:55:EE:9C:61",
            "securityv2",
            "com.radzdev.securityv2"
        )

        enableEdgeToEdge()
        setContent {
            Securityv2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Securityv2Theme {
        Greeting("Android")
    }
}

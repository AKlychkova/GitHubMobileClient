package tech.kts.metaclass.githubmobileclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import tech.kts.metaclass.githubmobileclient.platform.AndroidAuthLauncher
import tech.kts.metaclass.githubmobileclient.platform.initContext
import tech.kts.metaclass.githubmobileclient.ui.App

var authLauncher: AndroidAuthLauncher? = null

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        authLauncher = AndroidAuthLauncher(this)
        initContext(this)
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        authLauncher?.dispose()
        authLauncher = null
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
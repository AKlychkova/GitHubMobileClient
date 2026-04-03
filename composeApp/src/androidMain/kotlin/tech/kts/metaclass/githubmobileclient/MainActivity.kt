package tech.kts.metaclass.githubmobileclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.android.inject
import tech.kts.metaclass.githubmobileclient.platform.AndroidAuthRepository
import tech.kts.metaclass.githubmobileclient.ui.App
import kotlin.getValue

class MainActivity : ComponentActivity(){
    private val authRepository: AndroidAuthRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        authRepository.registerCaller(this)
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        authRepository.dispose()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
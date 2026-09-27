package __PROJECT_PACKAGE__.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import __PROJECT_PACKAGE__.shared.AppState
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var state by mutableStateOf(AppState())
    private val client = HttpClient(CIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("__PROJECT_NAME__", style = MaterialTheme.typography.headlineMedium)
                    Text("Ktor starter")
                    Text("Server: ${state.serverStatus}")
                }
            }
        }
        lifecycleScope.launch {
            state = state.copy(
                serverStatus = runCatching {
                    client.get(BuildConfig.STARTER_BASE_URL + "/health").bodyAsText()
                }.getOrElse { "unavailable" },
            )
        }
    }

    override fun onDestroy() {
        client.close()
        super.onDestroy()
    }
}
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.WeatherItemDomain

@Composable
fun WeatherList(weatherItems: List<WeatherItemDomain>) {
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        items(items = weatherItems, itemContent = { item ->
            WeatherListRendererItem(weatherItem = item)
        })
    }
}
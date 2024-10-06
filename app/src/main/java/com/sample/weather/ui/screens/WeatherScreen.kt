package com.sample.weather.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.weather.R
import com.sample.weather.data.model.WeatherResponse
import com.sample.weather.ui.theme.WeatherTheme
import com.sample.weather.ui.viewmodel.WeatherViewModel
import com.sample.weather.util.isNotNullOrEmpty
import timber.log.Timber


@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {

  val weather by viewModel.weatherState.collectAsState()
  var cityName by remember { mutableStateOf("") }

  WeatherComposeUI(cityName, weather, onCityNameChange = { cityName = it },
    onSearchClick = { viewModel.  getGeoCode(cityName) })

}

@Composable
fun WeatherComposeUI(cityName: String,
                     weather: WeatherUiState,
                     onCityNameChange:(cityName: String) -> Unit = {},
                     onSearchClick:() -> Unit = {} ){
  WeatherTheme {

    Column(
      modifier = Modifier,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      OutlinedTextField(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth()
          .testTag("enter_city_name"),
        value = cityName,
        onValueChange = {
          onCityNameChange(it)
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Search"
          )
        },

        shape = RoundedCornerShape(8.dp),
        enabled = true,
        isError = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        maxLines = 1,
        label = { Text(text = "Enter city name") }
      )

      if (cityName.isNotNullOrEmpty())
        Button(
          modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .testTag("search_button"),
          onClick = {
            if (cityName.isNotNullOrEmpty())
              onSearchClick()
          },
          shape = MaterialTheme.shapes.small,
          enabled = true
        ) {
          Text(text = "Search")
        }
    }

    // Show the weather CARD UI
    WeatherUi(weatherUiState = weather)
  }

}

@Composable
fun WeatherUi(weatherUiState: WeatherUiState) {
  when (weatherUiState) {
    is WeatherUiState.Success -> {
      WeatherCard(weatherResponse = weatherUiState.weatherResponse)
    }

    is WeatherUiState.Loading -> {
      Loading()
      Timber.tag("TAG").d("Loading")
    }
    is WeatherUiState.Error -> {
      Timber.tag("TAG").d("Error")
    }
    is WeatherUiState.Idle -> {
      //Do nothing
    }
  }

}

sealed interface WeatherUiState {
  data class Success(val weatherResponse: WeatherResponse) : WeatherUiState
  data object Error : WeatherUiState
  data object Loading : WeatherUiState
  data object Idle : WeatherUiState
}

@Composable
fun Loading(){
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
  ) {
    CircularProgressIndicator(
      modifier = Modifier
        .wrapContentWidth(Alignment.CenterHorizontally)
        .wrapContentHeight(Alignment.CenterVertically)
    )
  }

}

@Composable
fun WeatherCard(weatherResponse: WeatherResponse) {
  val weather = weatherResponse.weather.firstOrNull()
  val main = weatherResponse.main

  Surface(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
    shape = MaterialTheme.shapes.small,
  ) {

    Column(
      modifier = Modifier
        .padding(16.dp)
        .wrapContentWidth()
    ) {

      Text(
        text = "Now",
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Start
      )

      Row(
        modifier = Modifier
          .padding(top = 16.dp)
          .fillMaxWidth()
      ) {

        Column(
          modifier = Modifier
            .weight(1f)
        ) {
          Text(
            text = "${main.temp.toInt()}°",
            style = MaterialTheme.typography.headlineSmall
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = " High: ${main.temp_min.toInt()}°/ Low: ${main.temp_max.toInt()}°",
            style = MaterialTheme.typography.bodySmall
          )
        }

        Column {

          Text(
            text = "${weather?.description}".uppercase(),
            style = MaterialTheme.typography.titleSmall
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Feels like ${main.feels_like.toInt()}°",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End
          )
        }

      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        WeatherItemCard(
          "Wind",
          "${weatherResponse.wind.speed} m/h",
          ImageVector.vectorResource(id = R.drawable.ic_wind_24),
        )
        WeatherItemCard(
          "Humidity",
          "${main.humidity} %",
          ImageVector.vectorResource(id = R.drawable.ic_humidity_24),
        )
        WeatherItemCard(
          "Pressure",
          "${main.pressure} hPa",
          ImageVector.vectorResource(id = R.drawable.ic_pressure_24),
        )
      }
    }
  }
}

@Composable
fun WeatherItemCard(title: String, description: String, icon: ImageVector) {
  Card(
    modifier = Modifier
      .padding(8.dp)
      .width(100.dp)
      .shadow(
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
      )
  ) {

    Column(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall
      )

      Icon(
        modifier = Modifier
          .padding(8.dp)
          .wrapContentWidth(),
        imageVector = icon,
        contentDescription = "$title icon"
      )

      Text( modifier = Modifier,
        text = description,
        style = MaterialTheme.typography.bodySmall
      )
    }
  }
}


@Preview(showBackground = true)
@Composable
fun WeatherUiPreview() {
  WeatherComposeUI("Salt Lake City", WeatherUiState.Idle, {}, {})
}

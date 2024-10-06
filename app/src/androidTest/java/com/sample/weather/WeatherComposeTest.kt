package com.sample.weather

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sample.weather.ui.screens.WeatherComposeUI
import com.sample.weather.ui.screens.WeatherUiState
import dagger.hilt.android.testing.CustomTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@CustomTestApplication(WeatherApp::class)
class WeatherAppTestApplication : Application()

@RunWith(AndroidJUnit4::class)
class WeatherComposeTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  // Verify that the WeatherScreen is displayed
  @Test
  fun testWeatherScreen_Search_button_shown() {
    composeTestRule.setContent {
      WeatherComposeUI("Boston", WeatherUiState.Idle, {}, {}, {})
    }
    composeTestRule.onNodeWithTag("enter_city_name")
      .performTextInput("Salt Lake city")

    composeTestRule.onNodeWithTag("search_button").assertIsDisplayed()
  }
}

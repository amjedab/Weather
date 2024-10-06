# Weather App

This app displays the current weather conditions. It is implemented using the following technologies:

- **Kotlin**
- **MVVM (Model-View-ViewModel)**
- **Retrofit**
- **Hilt**
- **Jetpack Compose**
- **Coroutines**

## Weather Data
To display weather details, the app utilizes the OpenWeatherMap API:
- [OpenWeatherMap](https://openweathermap.org)
- API endpoint:  
  `https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid={API key}`

## Location Data
For obtaining the latitude and longitude, the app uses the Geocoder API:
- [Google Maps Geocoder](https://maps.googleapis.com/)
- API endpoint:  
  `https://maps.googleapis.com/maps/api/geocode/json?address=Boston&key={API key}`

## API Keys
To function properly, the app requires API keys for both OpenWeatherMap and Google Maps Geocoder. Once you have obtained the API keys, update the values in the `String.xml` file:

**Location**:  
`Weather/app/src/main/res/values/String.xml`

```xml
<string name="geo_code_api_key">XYZ</string>
<string name="open_weather_api_key">XYZ</string>
```

![Screenshot_20241006_102751](https://github.com/user-attachments/assets/9179a2cc-794f-4510-b1f4-89649643a6c1)

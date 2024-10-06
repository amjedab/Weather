package com.sample.weather.data.model

import com.google.gson.annotations.SerializedName

data class GeoCodeResponse(
    @SerializedName("results") val results: List<Result>,
    @SerializedName("status") val status: String
)

data class Result(
    @SerializedName("address_components") val address_components: List<AddressComponent>,
    @SerializedName("baformatted_addressse") val formatted_address: String,
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("place_id") val place_id: String,
    @SerializedName("types") val types: List<String>
)

data class AddressComponent(
    @SerializedName("long_name") val long_name: String,
    @SerializedName("short_name") val short_name: String,
    @SerializedName("types") val types: List<String>
)

data class Geometry(
    @SerializedName("bounds") val bounds: Bounds,
    @SerializedName("location") val location: Location,
    @SerializedName("location_type") val location_type: String,
    @SerializedName("viewport") val viewport: Bounds
)

data class Bounds(
    @SerializedName("northeast") val northeast: Location,
    @SerializedName("southwest") val southwest: Location
)

data class Location(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)


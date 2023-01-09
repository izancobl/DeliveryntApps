package PAE.deliverynt_backend.api

import PAE.deliverynt_backend.utils.GEOAPI_API_KEY
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

fun getRoutePlannerReponse(routeTemplateJson: String): String {
    val client: OkHttpClient = OkHttpClient().newBuilder().build()

    val mediaType: MediaType? = "application/json".toMediaTypeOrNull()
    val body = routeTemplateJson.toRequestBody(mediaType)

    val request: Request = Request.Builder()
        .url("https://api.geoapify.com/v1/routeplanner?apiKey=$GEOAPI_API_KEY")
        .method("POST", body)
        .addHeader("Content-Type", "application/json")
        .build()


    return client.newCall(request).execute().body?.string() ?: "500 Server Error"
}

fun getGeolocation(text: String): String {
    val client: OkHttpClient = OkHttpClient().newBuilder().build()
    val request: Request = Request.Builder()
        .url("https://api.geoapify.com/v1/geocode/search?text=${text}&apiKey=${GEOAPI_API_KEY}")
        .header("Content-Type", "application/json")
        .build()

    return client.newCall(request).execute().body?.string() ?: "500 Server Error"
}

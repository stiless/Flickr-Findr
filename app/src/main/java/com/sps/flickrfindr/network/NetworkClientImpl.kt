package com.sps.flickrfindr.network

import com.sps.flickrfindr.models.HttpResponse
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class NetworkClientImpl : NetworkClient {
    override fun getHttpResponse(url: String): HttpResponse {
        with(URL(url).openConnection() as HttpURLConnection) {
            var responseBody = ""
            inputStream.bufferedReader().use {
                responseBody = it.lines()
                    .collect(Collectors.joining(" "))
            }
            return HttpResponse(responseBody)
        }
    }
}
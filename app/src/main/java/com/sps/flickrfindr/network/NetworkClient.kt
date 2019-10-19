package com.sps.flickrfindr.network

import com.sps.flickrfindr.models.HttpResponse

interface NetworkClient {
    fun getHttpResponse(url: String): HttpResponse
}
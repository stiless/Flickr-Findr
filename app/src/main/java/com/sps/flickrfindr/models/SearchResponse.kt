package com.sps.flickrfindr.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val stat: String,
    val photos: Photos
)
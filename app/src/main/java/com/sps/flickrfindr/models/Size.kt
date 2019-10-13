package com.sps.flickrfindr.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Size(
    val label: String,
    val source: String
)
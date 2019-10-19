package com.sps.flickrfindr.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoSizeResponse(
    val stat: String,
    val sizes: Sizes
)
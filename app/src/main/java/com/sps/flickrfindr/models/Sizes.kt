package com.sps.flickrfindr.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sizes(val size: List<Size>)
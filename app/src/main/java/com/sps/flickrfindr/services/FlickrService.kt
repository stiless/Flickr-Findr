package com.sps.flickrfindr.services

import com.sps.flickrfindr.models.PhotoSizeResponse
import com.sps.flickrfindr.models.SearchResponse
import io.reactivex.Single

interface FlickrService {
    fun getPhotosWithSearch(searchString: String, numResults: Int = 25, pageNumber: Int = 1): Single<SearchResponse>
    fun getPhotoSizes(photoId: String): Single<PhotoSizeResponse>
}
package com.sps.flickrfindr.respositories

import com.sps.flickrfindr.PhotoListItem
import io.reactivex.Single

interface PhotoRepository {
    fun getPhotos(query: String, page: Int): Single<List<PhotoListItem>>
}
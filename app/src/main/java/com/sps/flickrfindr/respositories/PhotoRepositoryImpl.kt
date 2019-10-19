package com.sps.flickrfindr.respositories

import com.sps.flickrfindr.PhotoListItem
import com.sps.flickrfindr.services.FlickrService
import io.reactivex.Single

class PhotoRepositoryImpl(private val serviceImpl: FlickrService) : PhotoRepository {

    override fun getPhotos(query: String, page: Int): Single<List<PhotoListItem>> {
        return serviceImpl.getPhotosWithSearch(query, pageNumber = page)
            .flattenAsObservable { searchResponse -> searchResponse.photos.photo }
            .flatMapSingle { photo ->
                serviceImpl.getPhotoSizes(photo.id)
                    .map {
                        PhotoListItem(
                            it.sizes.size.last().source,
                            photo.title
                        )
                    }
            }
            .toList()
    }
}
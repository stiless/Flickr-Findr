package com.sps.flickrfindr.respositories

import com.sps.flickrfindr.PhotoListItem
import com.sps.flickrfindr.models.PhotoSizeResponse
import com.sps.flickrfindr.services.FlickrService
import io.reactivex.Single

class PhotoRepositoryImpl(private val serviceImpl: FlickrService) : PhotoRepository {

    companion object {
        private const val THUMBNAIL_LABEL = "Square"
        private const val ORIGINAL_LABEL = "Original"
    }

    override fun getPhotos(query: String): Single<List<PhotoListItem>> {
        return serviceImpl.getPhotosWithSearch(query)
            .flattenAsObservable { searchResponse -> searchResponse.photos.photo }
            .flatMapSingle { photo ->
                serviceImpl.getPhotoSizes(photo.id)
                    .map {
                        println(it.sizes.size)
                        PhotoListItem(
                            getImageSource(it, THUMBNAIL_LABEL),
                            getImageSource(it, ORIGINAL_LABEL), photo.title
                        )
                    }
            }
            .toList()
    }

    private fun getImageSource(photoSizeResponse: PhotoSizeResponse, label: String): String {
        return photoSizeResponse.sizes.size.first { it.label == label }.source
    }
}
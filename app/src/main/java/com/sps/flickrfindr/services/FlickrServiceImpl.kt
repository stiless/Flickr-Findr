package com.sps.flickrfindr.services

import com.sps.flickrfindr.models.PhotoSizeResponse
import com.sps.flickrfindr.models.SearchResponse
import com.sps.flickrfindr.network.NetworkClient
import com.squareup.moshi.Moshi
import io.reactivex.Single

class FlickrServiceImpl(private val networkClient: NetworkClient) : FlickrService {

    companion object {
        private const val BASE_URL = "https://www.flickr.com/services/rest/"
        private const val API_KEY = "1508443e49213ff84d566777dc211f2a"
        private const val SEARCH_METHOD = "flickr.photos.search"
        private const val PHOTO_SIZE_METHOD = "flickr.photos.getSizes"
        private const val SEARCH_URL =
            "$BASE_URL?method=$SEARCH_METHOD&api_key=$API_KEY&format=json&nojsoncallback=1"
        private const val PHOTO_INFO_URL =
            "$BASE_URL?method=$PHOTO_SIZE_METHOD&api_key=$API_KEY&format=json&nojsoncallback=1"
    }

    override fun getPhotosWithSearch(
        searchString: String,
        numResults: Int,
        pageNumber: Int
    ): Single<SearchResponse> {
        return Single.fromCallable { networkClient.getHttpResponse("$SEARCH_URL&per_page=$numResults&text=$searchString&page=$pageNumber") }
            .map { deserializeSearchResponse(it.body) }
    }

    override fun getPhotoSizes(photoId: String): Single<PhotoSizeResponse> {
        return Single.fromCallable { networkClient.getHttpResponse("$PHOTO_INFO_URL&photo_id=$photoId") }
            .map { deserializePhotoInfoResponse(it.body) }
    }

    private fun deserializeSearchResponse(jsonString: String): SearchResponse? {
        return moshiBuilder().adapter(SearchResponse::class.java).fromJson(jsonString)
    }

    private fun deserializePhotoInfoResponse(jsonString: String): PhotoSizeResponse? {
        return moshiBuilder().adapter(PhotoSizeResponse::class.java).fromJson(jsonString)
    }

    private fun moshiBuilder(): Moshi {
        return Moshi.Builder().build()
    }
}
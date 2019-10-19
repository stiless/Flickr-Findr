package com.sps.flickrfindr.respositories

import com.sps.flickrfindr.PhotoListItem
import com.sps.flickrfindr.models.*
import com.sps.flickrfindr.services.FlickrServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test

class PhotoRepositoryImplTest {

    @Test
    fun `getPhotos should call getPhotosWithSearch with correct query`() {
        val service = mockk<FlickrServiceImpl>()
        every { service.getPhotosWithSearch(any()) } returns Single.just(mockk())
        val repository = PhotoRepositoryImpl(service)

        repository.getPhotos("dogs", 1).test()

        verify {
            service.getPhotosWithSearch("dogs")
        }
    }

    @Test
    fun `getPhotos should call getPhotoSizes for each photo returned from getPhotosWithSearch`() {
        val service = mockk<FlickrServiceImpl>()
        every { service.getPhotosWithSearch(any()) } returns Single.just(
            SearchResponse(
                "ok",
                Photos(listOf(Photo("1", "photo1"), Photo("2", "photo2")))
            )
        )
        every { service.getPhotoSizes("1") } returns Single.just(
            PhotoSizeResponse("ok", Sizes(listOf(Size("Square", "thumbnail1"), Size("Original", "original1"))))
        )
        every { service.getPhotoSizes("2") } returns Single.just(
            PhotoSizeResponse("ok", Sizes(listOf(Size("Square", "thumbnail2"), Size("Original", "original2"))))
        )
        val repository = PhotoRepositoryImpl(service)

        repository.getPhotos("dogs", 1).test()

        verify {
            service.getPhotoSizes("1")
            service.getPhotoSizes("2")
        }
    }

    @Test
    fun `getPhotos should emit error when getPhotosWithSearch errors`() {
        val throwable = Throwable("error")
        val service = mockk<FlickrServiceImpl>()
        every { service.getPhotosWithSearch(any()) } returns Single.error(throwable)
        val repository = PhotoRepositoryImpl(service)

        val subscriber = repository.getPhotos("dogs", 1).test()

        subscriber.assertError(throwable)
    }

    @Test
    fun `getPhotos should emit error when getPhotoSizes errors`() {
        val throwable = Throwable("error")
        val service = mockk<FlickrServiceImpl>()
        every { service.getPhotosWithSearch(any()) } returns Single.just(
            SearchResponse(
                "ok",
                Photos(listOf(Photo("1", "photo1"), Photo("2", "photo2")))
            )
        )
        every { service.getPhotoSizes(any()) } returns Single.error(throwable)
        val repository = PhotoRepositoryImpl(service)

        val subscriber = repository.getPhotos("dogs", 1).test()

        subscriber.assertError(throwable)
    }

    @Test
    fun `getPhotos should return list of PhotoListItems when getPhotosWithSearch succeeds`() {
        val service = mockk<FlickrServiceImpl>()
        every { service.getPhotosWithSearch(any()) } returns Single.just(
            SearchResponse(
                "ok",
                Photos(listOf(Photo("1", "photo1"), Photo("2", "photo2")))
            )
        )
        every { service.getPhotoSizes("1") } returns Single.just(
            PhotoSizeResponse("ok", Sizes(listOf(Size("Square", "thumbnail1"), Size("Original", "original1"))))
        )
        every { service.getPhotoSizes("2") } returns Single.just(
            PhotoSizeResponse("ok", Sizes(listOf(Size("Square", "thumbnail2"), Size("Original", "original2"))))
        )
        val repository = PhotoRepositoryImpl(service)

        val subscriber = repository.getPhotos("dogs", 1).test()

        subscriber.assertValue(
            listOf(
                PhotoListItem("original1", "photo1"),
                PhotoListItem("original2", "photo2")
            )
        )
    }
}
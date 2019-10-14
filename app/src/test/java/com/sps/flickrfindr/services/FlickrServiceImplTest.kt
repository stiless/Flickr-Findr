package com.sps.flickrfindr.services

import com.sps.flickrfindr.models.HttpResponse
import com.sps.flickrfindr.network.NetworkClient
import com.squareup.moshi.JsonEncodingException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class FlickrServiceImplTest {

    @Test
    fun `getPhotosWithSearch should call getHttpResponse with search URL and page 1 of 25 results`() {
        val networkClient = mockk<NetworkClient>()
        val service = FlickrServiceImpl(networkClient)

        service.getPhotosWithSearch("dog").test()

        verify {
            networkClient.getHttpResponse("https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=1508443e49213ff84d566777dc211f2a&format=json&nojsoncallback=1&per_page=25&text=dog&page=1")
        }
    }

    @Test
    fun `getPhotosWithSearch should call getHttpResponse with search URL and defined page number and number of results`() {
        val networkClient = mockk<NetworkClient>()
        val service = FlickrServiceImpl(networkClient)

        service.getPhotosWithSearch("cats", numResults = 45, pageNumber = 5).test()

        verify {
            networkClient.getHttpResponse("https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=1508443e49213ff84d566777dc211f2a&format=json&nojsoncallback=1&per_page=45&text=cats&page=5")
        }
    }

    @Test
    fun `getPhotosWithSearch should emit an error when getHttpResponse throws an exception`() {
        val throwable = Throwable("something went wrong")
        val networkClient = mockk<NetworkClient>()
        every { networkClient.getHttpResponse(any()) } throws throwable
        val service = FlickrServiceImpl(networkClient)

        val response = service.getPhotosWithSearch("blah").test()

        response.assertError(throwable)
    }

    @Test
    fun `getPhotosWithSearch should emit an error when getHttpResponse returns HttpResponse with invalid json in body`() {
        val networkClient = mockk<NetworkClient>()
        val httpResponse = mockk<HttpResponse>()
        every { httpResponse.body } returns "lksajglsadglkdsgjlfajldj"
        every { networkClient.getHttpResponse(any()) } returns httpResponse
        val service = FlickrServiceImpl(networkClient)

        val response = service.getPhotosWithSearch("blah").test()

        response.assertError(JsonEncodingException::class.java)
    }

    @Test
    fun `getPhotosWithSearch should emit SearchResponse when getHttpResponse returns HttpResponse with valid json in body`() {
        val networkClient = mockk<NetworkClient>()
        val httpResponse = mockk<HttpResponse>()
        every { httpResponse.body } returns "{\"photos\":{\"page\":1,\"pages\":125864,\"perpage\":2,\"total\":\"251728\",\"photo\":[{\"id\":\"48882929922\",\"owner\":\"183194104@N02\",\"secret\":\"15e97c86da\",\"server\":\"65535\",\"farm\":66,\"title\":\"Friendship has no rules.\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"48882922757\",\"owner\":\"182066733@N05\",\"secret\":\"8aec5f0edc\",\"server\":\"65535\",\"farm\":66,\"title\":\"What Dog Breed Are You?\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"ok\"}"
        every { networkClient.getHttpResponse(any()) } returns httpResponse
        val service = FlickrServiceImpl(networkClient)

        val response = service.getPhotosWithSearch("dog").test()

        val searchResponse = response.values()[0]
        assertThat(searchResponse.stat).isEqualTo("ok")
        assertThat(searchResponse.photos.photo[0].id).isEqualTo("48882929922")
        assertThat(searchResponse.photos.photo[0].title).isEqualTo("Friendship has no rules.")
        assertThat(searchResponse.photos.photo[1].id).isEqualTo("48882922757")
        assertThat(searchResponse.photos.photo[1].title).isEqualTo("What Dog Breed Are You?")
    }

    @Test
    fun `getPhotoSizes should call getHttpResponse with photo info URL and photo ID`() {
        val networkClient = mockk<NetworkClient>()
        val service = FlickrServiceImpl(networkClient)

        service.getPhotoSizes("123456").test()

        verify {
            networkClient.getHttpResponse("https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=1508443e49213ff84d566777dc211f2a&format=json&nojsoncallback=1&photo_id=123456")
        }
    }

    @Test
    fun `getPhotoSizes should emit an error when getHttpResponse throws an exception`() {
        val throwable = Throwable("something went wrong")
        val networkClient = mockk<NetworkClient>()
        every { networkClient.getHttpResponse(any()) } throws throwable
        val service = FlickrServiceImpl(networkClient)

        val response = service.getPhotoSizes("636436").test()

        response.assertError(throwable)
    }

    @Test
    fun `getPhotoSizes should emit an error when getHttpResponse returns HttpResponse with invalid json in body`() {
        val networkClient = mockk<NetworkClient>()
        val httpResponse = mockk<HttpResponse>()
        every { httpResponse.body } returns "lksajglsadglkdsgjlfajldj"
        every { networkClient.getHttpResponse(any()) } returns httpResponse
        val service = FlickrServiceImpl(networkClient)

        val response = service.getPhotoSizes("7567567").test()

        response.assertError(JsonEncodingException::class.java)
    }

    @Test
    fun `getPhotoSizes should emit PhotoInfoResponse when getHttpResponse returns HttpResponse with valid json in body`() {
        val networkClient = mockk<NetworkClient>()
        val httpResponse = mockk<HttpResponse>()
        every { httpResponse.body } returns "{\"sizes\":{\"canblog\":0,\"canprint\":0,\"candownload\":1,\"size\":[{\"label\":\"Square\",\"width\":75,\"height\":75,\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5_s.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/sq/\",\"media\":\"photo\"},{\"label\":\"Large Square\",\"width\":\"150\",\"height\":\"150\",\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5_q.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/q/\",\"media\":\"photo\"},{\"label\":\"Thumbnail\",\"width\":100,\"height\":59,\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5_t.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/t/\",\"media\":\"photo\"},{\"label\":\"Small\",\"width\":\"240\",\"height\":\"141\",\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5_m.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/s/\",\"media\":\"photo\"},{\"label\":\"Small 320\",\"width\":\"320\",\"height\":\"188\",\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5_n.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/n/\",\"media\":\"photo\"},{\"label\":\"Medium\",\"width\":\"500\",\"height\":\"294\",\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/m/\",\"media\":\"photo\"},{\"label\":\"Medium 640\",\"width\":\"640\",\"height\":\"376\",\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5_z.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/z/\",\"media\":\"photo\"},{\"label\":\"Medium 800\",\"width\":\"800\",\"height\":\"470\",\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5_c.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/c/\",\"media\":\"photo\"},{\"label\":\"Large\",\"width\":\"1024\",\"height\":\"602\",\"source\":\"https://live.staticflickr.com/65535/48883006117_b208d323b5_b.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/l/\",\"media\":\"photo\"},{\"label\":\"Original\",\"width\":\"1200\",\"height\":\"705\",\"source\":\"https://live.staticflickr.com/65535/48883006117_133bdcd7ae_o.jpg\",\"url\":\"https://www.flickr.com/photos/30037912@N03/48883006117/sizes/o/\",\"media\":\"photo\"}]},\"stat\":\"ok\"}"
        every { networkClient.getHttpResponse(any()) } returns httpResponse
        val service = FlickrServiceImpl(networkClient)

        val response = service.getPhotoSizes("48883006117").test()

        val photoSizeResponse = response.values()[0]
        assertThat(photoSizeResponse.stat).isEqualTo("ok")
        assertThat(photoSizeResponse.sizes.size[0].label).isEqualTo("Square")
        assertThat(photoSizeResponse.sizes.size[0].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5_s.jpg")
        assertThat(photoSizeResponse.sizes.size[1].label).isEqualTo("Large Square")
        assertThat(photoSizeResponse.sizes.size[1].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5_q.jpg")
        assertThat(photoSizeResponse.sizes.size[2].label).isEqualTo("Thumbnail")
        assertThat(photoSizeResponse.sizes.size[2].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5_t.jpg")
        assertThat(photoSizeResponse.sizes.size[3].label).isEqualTo("Small")
        assertThat(photoSizeResponse.sizes.size[3].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5_m.jpg")
        assertThat(photoSizeResponse.sizes.size[4].label).isEqualTo("Small 320")
        assertThat(photoSizeResponse.sizes.size[4].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5_n.jpg")
        assertThat(photoSizeResponse.sizes.size[5].label).isEqualTo("Medium")
        assertThat(photoSizeResponse.sizes.size[5].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5.jpg")
        assertThat(photoSizeResponse.sizes.size[6].label).isEqualTo("Medium 640")
        assertThat(photoSizeResponse.sizes.size[6].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5_z.jpg")
        assertThat(photoSizeResponse.sizes.size[7].label).isEqualTo("Medium 800")
        assertThat(photoSizeResponse.sizes.size[7].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5_c.jpg")
        assertThat(photoSizeResponse.sizes.size[8].label).isEqualTo("Large")
        assertThat(photoSizeResponse.sizes.size[8].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_b208d323b5_b.jpg")
        assertThat(photoSizeResponse.sizes.size[9].label).isEqualTo("Original")
        assertThat(photoSizeResponse.sizes.size[9].source).isEqualTo("https://live.staticflickr.com/65535/48883006117_133bdcd7ae_o.jpg")
        
    }
}
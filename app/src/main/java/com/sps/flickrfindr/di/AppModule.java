package com.sps.flickrfindr.di;

import com.sps.flickrfindr.network.NetworkClient;
import com.sps.flickrfindr.network.NetworkClientImpl;
import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.respositories.PhotoRepositoryImpl;
import com.sps.flickrfindr.services.FlickrService;
import com.sps.flickrfindr.services.FlickrServiceImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    FlickrService providesFlickrService(NetworkClient networkClient) {
        return new FlickrServiceImpl(networkClient);
    }

    @Provides
    NetworkClient providesNetworkClient() {
        return new NetworkClientImpl();
    }

    @Provides
    PhotoRepository providesPhotoRepository(FlickrService service) {
        return new PhotoRepositoryImpl(service);
    }
}

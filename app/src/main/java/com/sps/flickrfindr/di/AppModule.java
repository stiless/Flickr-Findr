package com.sps.flickrfindr.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.sps.flickrfindr.network.NetworkClient;
import com.sps.flickrfindr.network.NetworkClientImpl;
import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.respositories.PhotoRepositoryImpl;
import com.sps.flickrfindr.respositories.SearchHistoryRepository;
import com.sps.flickrfindr.respositories.SearchHistoryRepositoryImpl;
import com.sps.flickrfindr.services.FlickrService;
import com.sps.flickrfindr.services.FlickrServiceImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    FlickrService providesFlickrService(NetworkClient networkClient) {
        return new FlickrServiceImpl(networkClient);
    }

    @Provides
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences("FLICKR_FINDR", 0);
    }

    @Provides
    NetworkClient providesNetworkClient() {
        return new NetworkClientImpl();
    }

    @Provides
    PhotoRepository providesPhotoRepository(FlickrService service) {
        return new PhotoRepositoryImpl(service);
    }

    @Provides
    SearchHistoryRepository providesSearchHistoryRepository(SharedPreferences sharedPreferences) {
        return new SearchHistoryRepositoryImpl(sharedPreferences);
    }
}

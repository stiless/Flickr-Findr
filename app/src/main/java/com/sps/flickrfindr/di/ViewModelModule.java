package com.sps.flickrfindr.di;

import androidx.lifecycle.ViewModel;
import com.sps.flickrfindr.MainViewModel;
import com.sps.flickrfindr.PhotoListViewModel;
import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.respositories.SearchHistoryRepository;
import com.sps.flickrfindr.utils.SchedulingUtil;
import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

import javax.inject.Provider;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    ViewModel providesMainViewModel(SearchHistoryRepository searchHistoryRepository) {
        return new MainViewModel(searchHistoryRepository);
    }

    @Provides
    @IntoMap
    @ViewModelKey(PhotoListViewModel.class)
    ViewModel providesPhotoListViewModel(SchedulingUtil schedulingUtil,
                                         PhotoRepository photoRepository,
                                         SearchHistoryRepository searchHistoryRepository) {
        return new PhotoListViewModel(schedulingUtil, photoRepository, searchHistoryRepository);
    }
}

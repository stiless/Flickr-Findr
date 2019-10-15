package com.sps.flickrfindr.di;

import androidx.lifecycle.ViewModel;
import com.sps.flickrfindr.PhotoListViewModel;
import com.sps.flickrfindr.ViewModelFactory;
import com.sps.flickrfindr.respositories.PhotoRepository;
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
    @ViewModelKey(PhotoListViewModel.class)
    ViewModel providesPhotoListViewModel(SchedulingUtil schedulingUtil,
                                         PhotoRepository photoRepository) {
        return new PhotoListViewModel(schedulingUtil, photoRepository);
    }
}

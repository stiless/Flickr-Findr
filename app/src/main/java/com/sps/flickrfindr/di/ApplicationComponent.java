package com.sps.flickrfindr.di;

import com.sps.flickrfindr.application.FlickrFindrApplication;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, AndroidViewModule.class})
public interface ApplicationComponent {

    void inject(FlickrFindrApplication application);
}

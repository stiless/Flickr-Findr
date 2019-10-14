package com.sps.flickrfindr.di;

import com.sps.flickrfindr.PhotoListActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AndroidViewModule {

    @ContributesAndroidInjector
    abstract PhotoListActivity bindPhotoListActivity();
}

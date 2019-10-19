package com.sps.flickrfindr.di;

import com.sps.flickrfindr.MainActivity;
import com.sps.flickrfindr.PhotoListActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AndroidViewModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract PhotoListActivity bindPhotoListActivity();
}

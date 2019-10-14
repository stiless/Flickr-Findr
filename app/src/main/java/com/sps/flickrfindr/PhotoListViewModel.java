package com.sps.flickrfindr;

import androidx.lifecycle.ViewModel;
import com.sps.flickrfindr.adapters.PhotoListAdapter;
import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.utils.SchedulingUtil;

import javax.inject.Inject;

public class PhotoListViewModel extends ViewModel {

    private final SchedulingUtil schedulingUtil;
    private final PhotoRepository photoRepository;
    private final PhotoListAdapter adapter;

    @Inject
    public PhotoListViewModel(SchedulingUtil schedulingUtil,
                              PhotoRepository photoRepository,
                              PhotoListAdapter adapter) {
        this.schedulingUtil = schedulingUtil;
        this.photoRepository = photoRepository;
        this.adapter = adapter;
    }

    public PhotoListAdapter getAdapter() {
        return adapter;
    }

    void performSearch(String query) {
        photoRepository.getPhotos(query)
                       .compose(schedulingUtil.singleSchedulers())
                       .subscribe(adapter::updatePhotoList, Throwable::printStackTrace);
    }
}

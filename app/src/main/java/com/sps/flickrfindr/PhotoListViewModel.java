package com.sps.flickrfindr;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.utils.SchedulingUtil;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;
import java.util.List;

public class PhotoListViewModel extends ViewModel {

    private final SchedulingUtil schedulingUtil;
    private final PhotoRepository photoRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<PhotoListItem>> photoItemsList = new MutableLiveData<>();

    @Inject
    public PhotoListViewModel(SchedulingUtil schedulingUtil,
                              PhotoRepository photoRepository) {
        this.schedulingUtil = schedulingUtil;
        this.photoRepository = photoRepository;
    }

    void performSearch(String query) {
        compositeDisposable.add(photoRepository.getPhotos(query)
                                               .compose(schedulingUtil.singleSchedulers())
                                               .subscribe(photoItemsList::setValue, Throwable::printStackTrace));
    }

    LiveData<List<PhotoListItem>> getAllPhotos() {
        performSearch("cat");
        return photoItemsList;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}

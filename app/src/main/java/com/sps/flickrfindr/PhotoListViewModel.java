package com.sps.flickrfindr;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.respositories.SearchHistoryRepository;
import com.sps.flickrfindr.utils.SchedulingUtil;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

public class PhotoListViewModel extends ViewModel {

    private final SchedulingUtil schedulingUtil;
    private final PhotoRepository photoRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<PhotoListItem>> photoItemsList = new MutableLiveData<>();

    public PhotoListViewModel(SchedulingUtil schedulingUtil,
                              PhotoRepository photoRepository,
                              SearchHistoryRepository searchHistoryRepository) {
        this.schedulingUtil = schedulingUtil;
        this.photoRepository = photoRepository;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    void performSearch(String query) {
        searchHistoryRepository.addSearchToHistory(query);
        compositeDisposable.add(photoRepository.getPhotos(query)
                                               .compose(schedulingUtil.singleSchedulers())
                                               .subscribe(photoItemsList::postValue, Throwable::printStackTrace));
    }

    LiveData<List<PhotoListItem>> getAllPhotos() {
        return photoItemsList;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}

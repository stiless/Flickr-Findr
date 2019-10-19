package com.sps.flickrfindr;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.respositories.SearchHistoryRepository;
import com.sps.flickrfindr.utils.SchedulingUtil;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

public class PhotoListViewModel extends ViewModel {

    public final MutableLiveData<List<PhotoListItem>> photoItemsList = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isProgressBarVisible = new MutableLiveData<>(false);
    public final MutableLiveData<Boolean> isPreviousLinkVisible = new MutableLiveData<>(false);
    public final MutableLiveData<Boolean> isNextLinkVisible = new MutableLiveData<>(false);
    public final MutableLiveData<Boolean> didApiCallFail = new MutableLiveData<>(false);

    private final SchedulingUtil schedulingUtil;
    private final PhotoRepository photoRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String query = "";
    private int page = 1;

    public PhotoListViewModel(SchedulingUtil schedulingUtil,
                              PhotoRepository photoRepository,
                              SearchHistoryRepository searchHistoryRepository) {
        this.schedulingUtil = schedulingUtil;
        this.photoRepository = photoRepository;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    public void performSearch(String query, int page) {
        this.query = query;
        isProgressBarVisible.setValue(true);
        searchHistoryRepository.addSearchToHistory(query);
        compositeDisposable.add(photoRepository.getPhotos(query, page)
                                               .compose(schedulingUtil.singleSchedulers())
                                               .subscribe(photoList -> {
                                                   isPreviousLinkVisible.setValue(page > 1);
                                                   isNextLinkVisible.setValue(photoList.size() > 0);
                                                   isProgressBarVisible.setValue(false);
                                                   photoItemsList.setValue(photoList);
                                               }, e -> {
                                                   isPreviousLinkVisible.setValue(false);
                                                   isNextLinkVisible.setValue(false);
                                                   isProgressBarVisible.setValue(false);
                                                   didApiCallFail.setValue(true);
                                                   e.printStackTrace();
                                               }));
    }

    public void nextPage() {
        performSearch(query, ++page);
    }

    public void previousPage() {
        performSearch(query, --page);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}

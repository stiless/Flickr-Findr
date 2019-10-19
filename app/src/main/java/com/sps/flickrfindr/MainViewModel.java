package com.sps.flickrfindr;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.sps.flickrfindr.respositories.SearchHistoryRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final SearchHistoryRepository searchHistoryRepository;
    private final MutableLiveData<List<String>> searchHistory = new MutableLiveData<>();

    public MainViewModel(SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    LiveData<List<String>> getSearchHistory() {
        searchHistory.setValue(new ArrayList<>(searchHistoryRepository.getSearchHistory()));
        return searchHistory;
    }

    void deleteSearchHistoryItem(String searchString) {
        searchHistoryRepository.removeSearchFromHistory(searchString);
        searchHistory.setValue(new ArrayList<>(searchHistoryRepository.getSearchHistory()));
    }
}

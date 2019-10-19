package com.sps.flickrfindr;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.respositories.SearchHistoryRepository;
import com.sps.flickrfindr.utils.SchedulingUtil;
import io.reactivex.Single;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class PhotoListViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void performSearch_should_call_getPhotos() {
        SchedulingUtil schedulingUtil = mock(SchedulingUtil.class);
        SearchHistoryRepository searchHistoryRepository = mock(SearchHistoryRepository.class);
        when(schedulingUtil.singleSchedulers()).thenReturn(single -> single);
        PhotoRepository photoRepository = mock(PhotoRepository.class);
        List<PhotoListItem> photoList = Arrays.asList(new PhotoListItem("or", "title"), new PhotoListItem("or", "title"));
        when(photoRepository.getPhotos("dog", 1)).thenReturn(Single.just(photoList));
        PhotoListViewModel viewModel = new PhotoListViewModel(schedulingUtil, photoRepository, searchHistoryRepository);

        viewModel.performSearch("dog", 1);

        verify(photoRepository).getPhotos("dog", 1);
    }

    @Test
    public void performSearch_should_save_query_to_search_history() {
        SchedulingUtil schedulingUtil = mock(SchedulingUtil.class);
        SearchHistoryRepository searchHistoryRepository = mock(SearchHistoryRepository.class);
        when(schedulingUtil.singleSchedulers()).thenReturn(single -> single);
        PhotoRepository photoRepository = mock(PhotoRepository.class);
        List<PhotoListItem> photoList = Arrays.asList(new PhotoListItem("or", "title"), new PhotoListItem("or", "title"));
        when(photoRepository.getPhotos("dog", 1)).thenReturn(Single.just(photoList));
        PhotoListViewModel viewModel = new PhotoListViewModel(schedulingUtil, photoRepository, searchHistoryRepository);

        viewModel.performSearch("dog", 1);

        verify(searchHistoryRepository).addSearchToHistory("dog");
    }

    @Test
    public void performSearch_should_update_adapter_on_success() {
        SchedulingUtil schedulingUtil = mock(SchedulingUtil.class);
        SearchHistoryRepository searchHistoryRepository = mock(SearchHistoryRepository.class);
        when(schedulingUtil.singleSchedulers()).thenReturn(single -> single);
        PhotoRepository photoRepository = mock(PhotoRepository.class);
        List<PhotoListItem> photoList = Arrays.asList(new PhotoListItem("or", "title"), new PhotoListItem("or", "title"));
        when(photoRepository.getPhotos("dog", 1)).thenReturn(Single.just(photoList));
        Observer observer = mock(Observer.class);
        PhotoListViewModel viewModel = new PhotoListViewModel(schedulingUtil, photoRepository, searchHistoryRepository);
        viewModel.photoItemsList.observeForever(observer);

        viewModel.performSearch("dog", 1);

        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(observer).onChanged(captor.capture());
        assertThat(captor.getValue()).isEqualTo(Arrays.asList(new PhotoListItem("or", "title"), new PhotoListItem("or", "title")));
    }
}
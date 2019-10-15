package com.sps.flickrfindr;

import com.sps.flickrfindr.respositories.PhotoRepository;
import com.sps.flickrfindr.utils.SchedulingUtil;
import io.reactivex.Single;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class PhotoListViewModelTest {

    @Test
    public void performSearch_should_call_getPhotos() {
        SchedulingUtil schedulingUtil = mock(SchedulingUtil.class);
        when(schedulingUtil.singleSchedulers()).thenReturn(single -> single);
        PhotoRepository photoRepository = mock(PhotoRepository.class);
        List<PhotoListItem> photoList = Arrays.asList(new PhotoListItem("th", "or", "title"), new PhotoListItem("th", "or", "title"));
        when(photoRepository.getPhotos("dog")).thenReturn(Single.just(photoList));
        PhotoListViewModel viewModel = new PhotoListViewModel(schedulingUtil, photoRepository);

        viewModel.performSearch("dog");

        verify(photoRepository).getPhotos("dog");
    }

    @Test
    public void performSearch_should_update_adapter_on_success() {
        SchedulingUtil schedulingUtil = mock(SchedulingUtil.class);
        when(schedulingUtil.singleSchedulers()).thenReturn(single -> single);
        PhotoRepository photoRepository = mock(PhotoRepository.class);
        List<PhotoListItem> photoList = Arrays.asList(new PhotoListItem("th", "or", "title"), new PhotoListItem("th", "or", "title"));
        when(photoRepository.getPhotos("dog")).thenReturn(Single.just(photoList));
        PhotoListViewModel viewModel = new PhotoListViewModel(schedulingUtil, photoRepository);

        viewModel.performSearch("dog");
    }
}
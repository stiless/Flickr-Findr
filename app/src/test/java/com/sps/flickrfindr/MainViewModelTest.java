package com.sps.flickrfindr;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.sps.flickrfindr.respositories.SearchHistoryRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MainViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void getSearchHistory_retrieves_search_history_from_repository() {
        SearchHistoryRepository repository = mock(SearchHistoryRepository.class);
        when(repository.getSearchHistory()).thenReturn(new HashSet<>(Arrays.asList("search1", "search2", "search3")));
        MainViewModel viewModel = new MainViewModel(repository);

        assertThat(new HashSet<>(viewModel.getSearchHistory().getValue())).isEqualTo(new HashSet<>(Arrays.asList("search1", "search2", "search3")));
    }

    @Test
    public void deleteSearchHistoryItem_removes_search_history_item_from_repository() {
        SearchHistoryRepository repository = mock(SearchHistoryRepository.class);
        when(repository.getSearchHistory()).thenReturn(new HashSet<>(Arrays.asList("search1", "search2", "search3")));
        MainViewModel viewModel = new MainViewModel(repository);

        viewModel.deleteSearchHistoryItem("search2");

        verify(repository).removeSearchFromHistory("search2");
    }
}
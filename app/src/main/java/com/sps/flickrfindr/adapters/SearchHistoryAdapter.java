package com.sps.flickrfindr.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sps.flickrfindr.DeleteSearchHistoryItemClickListener;
import com.sps.flickrfindr.SearchHistoryItemClickListener;
import com.sps.flickrfindr.databinding.ItemSearchHistoryBinding;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder> {

    private final List<String> searchHistory;
    private final SearchHistoryItemClickListener searchHistoryClickListener;
    private final DeleteSearchHistoryItemClickListener deleteSearchHistoryItemClickListener;

    public SearchHistoryAdapter(List<String> searchHistory,
                                SearchHistoryItemClickListener searchHistoryClickListener,
                                DeleteSearchHistoryItemClickListener deleteSearchHistoryItemClickListener) {
        this.searchHistory = searchHistory;
        this.searchHistoryClickListener = searchHistoryClickListener;
        this.deleteSearchHistoryItemClickListener = deleteSearchHistoryItemClickListener;
    }

    @NonNull
    @Override
    public SearchHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSearchHistoryBinding binding = ItemSearchHistoryBinding.inflate(layoutInflater, parent, false);
        return new SearchHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryViewHolder holder, int position) {
        String item = searchHistory.get(position);
        holder.bind(item);
        holder.binding.searchTitle.setOnClickListener(view -> searchHistoryClickListener.onClickSearchHistoryItem(view, item));
        holder.binding.closeX.setOnClickListener(view -> deleteSearchHistoryItemClickListener.onClickDeleteSearchHistoryItem(view, item));
    }

    @Override
    public int getItemCount() {
        return searchHistory.size();
    }

    class SearchHistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemSearchHistoryBinding binding;

        SearchHistoryViewHolder(ItemSearchHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(String searchString) {
            binding.setSearchString(searchString);
            binding.executePendingBindings();
        }
    }
}

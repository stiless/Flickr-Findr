package com.sps.flickrfindr.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sps.flickrfindr.databinding.ItemSearchHistoryBinding;

import java.util.List;

public class SearchHistoryAdapater extends RecyclerView.Adapter<SearchHistoryAdapater.SearchHistoryViewHolder> {

    private final List<String> searchHistory;

    public SearchHistoryAdapater(List<String> searchHistory) {
        this.searchHistory = searchHistory;
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
//        holder.binding.photoItemContainer.setOnClickListener(view -> clickListener.onClick(view, item.getImageUrl(), item.getTitle()));
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

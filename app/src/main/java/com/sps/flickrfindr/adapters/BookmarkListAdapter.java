package com.sps.flickrfindr.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sps.flickrfindr.BookmarkItemClickListener;
import com.sps.flickrfindr.BookmarkListItem;
import com.sps.flickrfindr.databinding.ItemBookmarkBinding;

import java.util.ArrayList;
import java.util.List;

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.BookmarkListViewHolder> {

    private final BookmarkItemClickListener clickListener;

    private List<BookmarkListItem> bookmarkList = new ArrayList<>();

    public BookmarkListAdapter(BookmarkItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BookmarkListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBookmarkBinding binding = ItemBookmarkBinding.inflate(layoutInflater, parent, false);
        return new BookmarkListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkListViewHolder holder, int position) {
        BookmarkListItem item = bookmarkList.get(position);
        holder.bind(item);
        holder.binding.bookmarkContainer.setOnClickListener(view -> clickListener.onClick(view, item.getImageLocation(), item.getTitle()));
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public void setBookmarkList(List<BookmarkListItem> bookmarkList) {
        this.bookmarkList.clear();
        this.bookmarkList.addAll(bookmarkList);
        notifyDataSetChanged();
    }

    class BookmarkListViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookmarkBinding binding;

        BookmarkListViewHolder(ItemBookmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(BookmarkListItem item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}



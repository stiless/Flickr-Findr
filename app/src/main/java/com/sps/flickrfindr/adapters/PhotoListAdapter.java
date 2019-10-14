package com.sps.flickrfindr.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sps.flickrfindr.PhotoListItem;
import com.sps.flickrfindr.databinding.ItemPhotoBinding;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder> {

    private List<PhotoListItem> photoList = Collections.emptyList();

    @Inject
    public PhotoListAdapter() {
    }

    @NonNull
    @Override
    public PhotoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPhotoBinding binding = ItemPhotoBinding.inflate(layoutInflater, parent, false);
        return new PhotoListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoListViewHolder holder, int position) {
        PhotoListItem item = photoList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void updatePhotoList(List<PhotoListItem> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }

    class PhotoListViewHolder extends RecyclerView.ViewHolder {
        private ItemPhotoBinding binding;

        PhotoListViewHolder(ItemPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PhotoListItem item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}



package com.sps.flickrfindr.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sps.flickrfindr.PhotoItemClickListener;
import com.sps.flickrfindr.PhotoListItem;
import com.sps.flickrfindr.databinding.ItemPhotoBinding;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder> {

    private final List<PhotoListItem> photoList;
    private final PhotoItemClickListener clickListener;

    public PhotoListAdapter(List<PhotoListItem> photoList, PhotoItemClickListener clickListener) {
        this.photoList = photoList;
        this.clickListener = clickListener;
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
        holder.binding.photoItemContainer.setOnClickListener(view -> clickListener.onClick(view, item.getImageUrl(), item.getTitle()));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class PhotoListViewHolder extends RecyclerView.ViewHolder {
        private final ItemPhotoBinding binding;

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



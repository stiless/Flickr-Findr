package com.sps.flickrfindr;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sps.flickrfindr.adapters.PhotoListAdapter;
import com.sps.flickrfindr.databinding.ActivityPhotoListBinding;
import com.sps.flickrfindr.di.ViewModelFactory;

import dagger.android.AndroidInjection;

import javax.inject.Inject;
import java.util.List;

public class PhotoListActivity extends AppCompatActivity implements PhotoItemClickListener {

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        ActivityPhotoListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_list);
        PhotoListViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotoListViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        viewModel.getAllPhotos().observe(this, photoListItems -> updateRecyclerView(binding.recyclerView, photoListItems));

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(query);
            }
            viewModel.performSearch(query);
        }
    }

    @Override
    public void onClick(View view, String imageUrl, String title) {
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("URL", imageUrl);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }

    private void updateRecyclerView(RecyclerView recyclerView, List<PhotoListItem> photoListItems) {
        if (photoListItems.size() > 0) {
            PhotoListAdapter adapter = new PhotoListAdapter(photoListItems, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            (new AlertDialog.Builder(this))
                    .setTitle(R.string.no_results_found_title)
                    .setMessage(R.string.no_results_found_body)
                    .setCancelable(true)
                    .create()
                    .show();
        }
    }
}

package com.sps.flickrfindr;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.sps.flickrfindr.databinding.ActivityPhotoListBinding;
import dagger.android.AndroidInjection;

import javax.inject.Inject;

public class PhotoListActivity extends AppCompatActivity {

    @Inject
    PhotoListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        ActivityPhotoListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_list);

        binding.setViewModel(viewModel);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            viewModel.performSearch(query);
        }
    }

    public void loadPhoto(View view) {
        startActivity(new Intent(this, PhotoActivity.class));
    }
}

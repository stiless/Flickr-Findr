package com.sps.flickrfindr;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
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

        PhotoListAdapter adapter = new PhotoListAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        viewModel.photoItemsList.observe(this, photoListItems -> updateRecyclerView(adapter, photoListItems));
        viewModel.didApiCallFail.observe(this, this::showError);

        if (hasNetworkConnection()) {
            Intent intent = getIntent();
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(query);
                }
                viewModel.performSearch(query, 1);
            }
        } else {
            showNoNetworkConnectionDialog();
        }
    }

    @Override
    public void onClick(View view, String imageUrl, String title) {
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("URL", imageUrl);
        intent.putExtra("TITLE", title);
        intent.putExtra("SHOW_SAVE_BUTTON", true);
        startActivity(intent);
    }

    private void updateRecyclerView(PhotoListAdapter adapter, List<PhotoListItem> photoListItems) {
        if (photoListItems.size() > 0) {
            adapter.setPhotoList(photoListItems);
        } else {
            (new AlertDialog.Builder(this))
                    .setTitle(R.string.no_results_found_title)
                    .setMessage(R.string.no_results_found_body)
                    .setCancelable(true)
                    .create()
                    .show();
        }
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean hasConnection = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            hasConnection = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        return hasConnection;
    }

    private void showNoNetworkConnectionDialog() {
        (new AlertDialog.Builder(this))
                .setTitle(R.string.no_network_title)
                .setMessage(R.string.no_network_body)
                .setCancelable(true)
                .create()
                .show();
    }

    private void showError(boolean didApiFail) {
        if (didApiFail) {
            (new AlertDialog.Builder(this))
                    .setTitle(R.string.generic_error_title)
                    .setMessage(R.string.generic_error_body)
                    .setCancelable(true)
                    .create()
                    .show();
        }
    }
}

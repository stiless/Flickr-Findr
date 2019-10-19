package com.sps.flickrfindr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.sps.flickrfindr.adapters.BookmarkListAdapter;
import com.sps.flickrfindr.databinding.ActivityBookmarkListBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookmarkListActivity extends AppCompatActivity implements BookmarkItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityBookmarkListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bookmark_list);

        BookmarkListAdapter adapter = new BookmarkListAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        updateRecyclerView(adapter);
        getSupportActionBar().setTitle(R.string.bookmarks);
    }

    private void updateRecyclerView(BookmarkListAdapter adapter) {
        File dir = getExternalFilesDir("PICTURES");
        File[] files = dir.listFiles();

        if (files != null && files.length > 0) {
            List<BookmarkListItem> fileList = new ArrayList<>();
            for (File file : files) {
                fileList.add(new BookmarkListItem(file.getAbsolutePath(), file.getName()));
            }
            adapter.setBookmarkList(fileList);
        } else {
            (new AlertDialog.Builder(this))
                    .setTitle(R.string.no_results_found_title)
                    .setMessage(R.string.no_results_found_body)
                    .setCancelable(true)
                    .create()
                    .show();
        }
    }

    @Override
    public void onClick(View view, String imageLocation, String title) {
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("URL", imageLocation);
        intent.putExtra("TITLE", title);
        intent.putExtra("SHOW_SAVE_BUTTON", false);
        startActivity(intent);
    }
}

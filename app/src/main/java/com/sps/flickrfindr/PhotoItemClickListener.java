package com.sps.flickrfindr;

import android.view.View;

public interface PhotoItemClickListener {
    void onClick(View view, String imageUrl, String title);
}

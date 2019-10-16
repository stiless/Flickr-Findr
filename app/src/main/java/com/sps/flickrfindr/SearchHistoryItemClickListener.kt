package com.sps.flickrfindr

import android.view.View

interface SearchHistoryItemClickListener {
    fun onClickSearchHistoryItem(view: View, searchString: String)
}
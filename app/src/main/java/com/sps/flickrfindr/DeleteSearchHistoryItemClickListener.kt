package com.sps.flickrfindr

import android.view.View

interface DeleteSearchHistoryItemClickListener {
    fun onClickDeleteSearchHistoryItem(view: View, searchString: String)
}
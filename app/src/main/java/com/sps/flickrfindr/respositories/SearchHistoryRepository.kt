package com.sps.flickrfindr.respositories

interface SearchHistoryRepository {
    fun getSearchHistory(): Set<String>
    fun addSearchToHistory(searchString: String)
    fun removeSearchFromHistory(searchString: String)
}
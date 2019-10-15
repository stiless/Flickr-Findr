package com.sps.flickrfindr.respositories

import android.content.SharedPreferences

class SearchHistoryRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    SearchHistoryRepository {

    companion object {
        private const val SEARCH_HISTORY_KEY = "SEARCH_HISTORY"
    }

    override fun getSearchHistory(): Set<String> {
        return sharedPreferences.getStringSet(SEARCH_HISTORY_KEY, emptySet()) ?: emptySet()
    }

    override fun addSearchToHistory(searchString: String) {
        val searchHistory = getSearchHistory().toMutableSet()
        searchHistory.add(searchString)
        val editor = sharedPreferences.edit()
        editor.putStringSet("SEARCH_HISTORY", searchHistory)
        editor.apply()
    }

    override fun removeSearchFromHistory(searchString: String) {
        val searchHistory = getSearchHistory().toMutableSet()
        searchHistory.remove(searchString)
        val editor = sharedPreferences.edit()
        editor.putStringSet("SEARCH_HISTORY", searchHistory)
        editor.apply()
    }
}
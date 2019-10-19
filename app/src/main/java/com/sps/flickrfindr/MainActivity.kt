package com.sps.flickrfindr

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sps.flickrfindr.adapters.SearchHistoryAdapter
import com.sps.flickrfindr.databinding.ActivityMainBinding
import com.sps.flickrfindr.di.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SearchHistoryItemClickListener, DeleteSearchHistoryItemClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this

        viewModel.searchHistory.observe(
            this,
            Observer<List<String>> { searchHistory -> updateRecyclerView(binding.recyclerView, searchHistory) })
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.bookmarks -> {
                val intent = Intent(this, BookmarkListActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClickSearchHistoryItem(view: View, searchString: String) {
        val intent = Intent(this, PhotoListActivity::class.java)
        intent.action = Intent.ACTION_SEARCH
        intent.putExtra(SearchManager.QUERY, searchString)
        startActivity(intent)
    }

    override fun onClickDeleteSearchHistoryItem(view: View, searchString: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.history_delete_confirmation_title)
            .setMessage(getString(R.string.history_delete_confirmation_body, searchString))
            .setCancelable(true)
            .setNegativeButton(R.string.no_button) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.yes_button) { _, _ -> viewModel.deleteSearchHistoryItem(searchString) }
            .create()
            .show()
    }

    private fun updateRecyclerView(recyclerView: RecyclerView, searchHistory: List<String>) {
        val adapter = SearchHistoryAdapter(searchHistory, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter;
        adapter.notifyDataSetChanged()
    }
}

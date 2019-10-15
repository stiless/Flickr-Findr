package com.sps.flickrfindr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sps.flickrfindr.databinding.ActivityPhotoBinding
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityPhotoBinding>(this, R.layout.activity_photo)
        binding.url = intent.getStringExtra("URL")

        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        setSupportActionBar(toolbar)
        supportActionBar?.title = intent.getStringExtra("TITLE")
    }
}

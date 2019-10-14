package com.sps.flickrfindr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        setSupportActionBar(toolbar)
    }
}

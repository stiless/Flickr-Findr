package com.sps.flickrfindr

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sps.flickrfindr.databinding.ActivityPhotoBinding
import kotlinx.android.synthetic.main.activity_photo.*
import java.io.File
import java.io.FileOutputStream


class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo)
        binding.url = intent.getStringExtra("URL")
        binding.title = intent.getStringExtra("TITLE")
        binding.isSaveButtonVisible = intent.getBooleanExtra("SHOW_SAVE_BUTTON", true)

        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
    }

    fun navigateUp(view: View) {
        finish()
    }

    fun saveImage(view: View) {
        val imageFileName = "${binding.title}.jpg"
        val storageDir = getExternalFilesDir("PICTURES")

        var success = true
        storageDir?.let {
            if (!it.exists()) {
                success = storageDir.mkdirs()
            }
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            try {
                val fOut = FileOutputStream(imageFile)
                (binding.image.drawable as BitmapDrawable).bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
                Toast.makeText(applicationContext, R.string.successful_save_photo, LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, R.string.error_save_photo, LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}

package com.nanamare.starter.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.Size
import com.davemorrissey.labs.subscaleview.ImageSource
import com.nanamare.core.util.viewBinding
import com.nanamare.starter.databinding.ActivityPhotoDetailBinding
import com.nanamare.starter.util.stringExtra
import kotlinx.coroutines.launch

class PhotoDetailActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityPhotoDetailBinding::inflate)

    private val imageDownloadUrl by stringExtra("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadImage()
    }

    private fun loadImage() {
        lifecycleScope.launch {
            val request = ImageRequest.Builder(this@PhotoDetailActivity)
                .data(imageDownloadUrl)
                .size(Size.ORIGINAL)
                .build()
            imageLoader.execute(request).drawable?.toBitmap()?.let { bitmap ->
                binding.iv.setImage(ImageSource.bitmap(bitmap))
                binding.progressBar.isVisible = false
            }
        }
    }

    companion object {
        fun getIntent(context: Context, downloadUrl: String) =
            Intent(context, PhotoDetailActivity::class.java)
                .putExtra("imageDownloadUrl", downloadUrl)
    }
}
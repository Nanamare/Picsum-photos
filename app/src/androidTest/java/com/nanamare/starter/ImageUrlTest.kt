package com.nanamare.starter

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nanamare.data.BuildConfig
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class ImageUrlTest {

    @Test
    fun isSameImageUrl() {
        val id = Random.nextLong(from = 0, until = 100)

        // before
        val beforeImageUrl = "https://picsum.photos/id/$id"

        // after
        val afterImageUrl = Uri.parse(BuildConfig.BASE_URL)
            .buildUpon()
            .appendPath("id")
            .appendPath(id.toString())
            .build().toString()

        Assert.assertEquals(beforeImageUrl, afterImageUrl)
    }

}
package com.nanamare.starter.photo

import com.nanamare.domain.photo.model.Photo

object MockData {
    private val photo1 = Photo(
        id = 0,
        author = "Alejandro Escamilla",
        width = 5616,
        height = 3744,
        url = "https://unsplash.com/photos/yC-Yzbqy7PY",
        downloadUrl = "https://picsum.photos/id/0/5616/3744",
        imageUrl = "https://picsum.photos/id/0"
    )

    private val photo2 = Photo(
        id = 1025,
        author = "Matthew Wiebe",
        width = 4951,
        height = 3301,
        url = "https://unsplash.com/photos/U5rMrSI7Pn4",
        downloadUrl = "https://picsum.photos/id/1025/4951/3301",
        imageUrl = "https://picsum.photos/id/1025"
    )

    val photoList = listOf(photo1, photo2)
}
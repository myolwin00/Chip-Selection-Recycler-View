package com.emrys.chipselectionrecyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel: ViewModel() {

    private val categories = listOf<String>(
        "Action",
        "Sci-fi",
        "Horror",
        "Myanmar Movie",
        "English Movie",
        "Thin Char Movie",
        "Chemistry Movie",
        "Physics Movie",
        "Bio Movie",
        "Eco Movie",
        "Animal Movie",
        "Romantic Movie",
        "Taw Movie"
    )

    val categoryLive = MutableLiveData<List<String>>(categories)
}
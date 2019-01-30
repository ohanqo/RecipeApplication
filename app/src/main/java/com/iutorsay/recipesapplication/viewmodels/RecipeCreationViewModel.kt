package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

class RecipeCreationViewModel : ViewModel() {
    val currentName = MutableLiveData<String>()

    val currentDescription = MutableLiveData<String>()

}

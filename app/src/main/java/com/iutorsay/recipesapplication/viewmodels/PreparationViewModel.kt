package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.entities.Step

class PreparationViewModel : ViewModel() {
    var recipe: Recipe? = null

    val steps = MutableLiveData<List<Step>>()

    val title = MutableLiveData<String>()

    val currentStep = MutableLiveData<Step>()

    var currentTiming = MutableLiveData<String>()
}
package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.iutorsay.recipesapplication.data.entities.Ingredient

class RecipeCreationViewModel : ViewModel() {
    val currentName = MutableLiveData<String>()

    val currentDescription = MutableLiveData<String>()

    val currentIngredients = ArrayList<Ingredient>()

    val inputIngredientName = MutableLiveData<String>()

    val inputIngredientQuantity = MutableLiveData<String>()
}

package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.ViewModel
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository

class MainViewModel : ViewModel() {
    var recipes = RecipeRepository.getInstance().getAll()
    var recipesWithIngredient = RecipeRepository.getInstance().getAllWithIngredients()
}
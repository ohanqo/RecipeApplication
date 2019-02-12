package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.ViewModel
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository

class LibraryViewModel : ViewModel() {
    val favRecipes = RecipeRepository.getInstance().getFavorites()
}
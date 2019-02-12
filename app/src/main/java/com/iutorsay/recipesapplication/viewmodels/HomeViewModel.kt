package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.ViewModel
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository

class HomeViewModel : ViewModel() {
    val recipes = RecipeRepository.getInstance().getAll()
}

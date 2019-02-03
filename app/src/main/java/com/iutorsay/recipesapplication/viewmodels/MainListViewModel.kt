package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.ViewModel
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository

class MainListViewModel : ViewModel() {
    val recipes = RecipeRepository.getInstance().getAll()
}

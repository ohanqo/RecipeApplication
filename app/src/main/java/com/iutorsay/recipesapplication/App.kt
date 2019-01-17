package com.iutorsay.recipesapplication

import android.app.Application
import com.iutorsay.recipesapplication.data.AppDatabase
import com.iutorsay.recipesapplication.data.repositories.IngredientRepository
import com.iutorsay.recipesapplication.data.repositories.InstructionRepository
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val databaseInstance = AppDatabase.getInstance(this)
        RecipeRepository.initInstance(databaseInstance)
        IngredientRepository.initInstance(databaseInstance)
        InstructionRepository.initInstance(databaseInstance)
    }
}
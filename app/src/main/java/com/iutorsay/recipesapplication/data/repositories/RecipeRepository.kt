package com.iutorsay.recipesapplication.data.repositories

import android.os.AsyncTask
import com.iutorsay.recipesapplication.data.AppDatabase
import com.iutorsay.recipesapplication.data.entities.Recipe

class RecipeRepository private constructor(private val databaseInstance: AppDatabase) {
    private val recipeDao = databaseInstance.recipeDao()

    fun get(recipeId: Int) = recipeDao.get(recipeId)

    fun getAll() = recipeDao.getAll()

    fun getAllWithIngredients() = recipeDao.getAllWithIngredients()

    fun insert(recipe: Recipe) : Long = InsertAsyncTask().execute(recipe).get()

    companion object {
        @Volatile private lateinit var instance: RecipeRepository

        fun initInstance(databaseInstance: AppDatabase): RecipeRepository {
            instance = RecipeRepository(databaseInstance)
            return instance
        }

        fun getInstance() = instance
    }

    inner class InsertAsyncTask internal constructor() : AsyncTask<Recipe, Void, Long>() {
        override fun doInBackground(vararg params: Recipe): Long {
            return recipeDao.insert(params[0])
        }
    }
}
package com.iutorsay.recipesapplication.data.repositories

import android.os.AsyncTask
import com.iutorsay.recipesapplication.data.AppDatabase
import com.iutorsay.recipesapplication.data.dao.IngredientDao
import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.data.entities.Recipe

class IngredientRepository private constructor(private val databaseInstance: AppDatabase) {
    private val ingredientDao = databaseInstance.ingredientDao()

    fun getAll() = ingredientDao.getAll()

    fun getRecipeIngredients(recipeId: Int) = ingredientDao.getRecipeIngredients(recipeId)

    fun insertAll(ingredients: List<Ingredient>) = InsertAsyncTask(ingredientDao).execute(ingredients)

    companion object {
        @Volatile private lateinit var instance: IngredientRepository

        fun initInstance(databaseInstance: AppDatabase): IngredientRepository {
            instance = IngredientRepository(databaseInstance)
            return instance
        }

        fun getInstance() = instance

        class InsertAsyncTask internal constructor(private val ingredientDao: IngredientDao) : AsyncTask<List<Ingredient>, Void, Void>() {
            override fun doInBackground(vararg params: List<Ingredient>): Void? {
                ingredientDao.insertAll(params[0])
                return null
            }
        }
    }
}
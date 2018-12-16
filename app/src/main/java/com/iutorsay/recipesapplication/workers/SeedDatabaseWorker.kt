package com.iutorsay.recipesapplication.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.iutorsay.recipesapplication.data.AppDatabase
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.utilities.RECIPES_FILENAME

class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val recipeType = object : TypeToken<List<Recipe>>() {}.type
        var jsonReader: JsonReader? = null

        return try {
            val inputStream = applicationContext.assets.open(RECIPES_FILENAME)
            jsonReader = JsonReader(inputStream.reader())
            val recipeList: List<Recipe> = Gson().fromJson(jsonReader, recipeType)
            val database = AppDatabase.getInstance(applicationContext)
            database.recipeDao().insertAll(recipeList)
            recipeList.forEach { recipe ->
                recipe.ingredients.forEach { it.recipeId = recipe.recipeId }
                database.ingredientDao().insertAll(recipe.ingredients)
            }
            Result.SUCCESS
        } catch (ex: Exception) {
            Log.e("—SEEDING", "Error seeding database", ex)
            Result.FAILURE
        } finally {
            jsonReader?.close()
        }
    }
}
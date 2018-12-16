package com.iutorsay.recipesapplication.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.relations.RecipeWithIngredients

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<Recipe>)

    @Query("SELECT * FROM recipes WHERE id=:recipeId")
    fun get(recipeId: Int): LiveData<Recipe>

    @Query("SELECT * FROM recipes")
    fun getAll(): LiveData<List<Recipe>>

    @Transaction
    @Query("SELECT * FROM recipes")
    fun getAllWithIngredients(): LiveData<List<RecipeWithIngredients>>
}
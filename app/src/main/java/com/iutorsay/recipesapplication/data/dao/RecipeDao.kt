package com.iutorsay.recipesapplication.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.relations.RecipeWithIngredients

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<Recipe>)

    @Query("SELECT * FROM recipes WHERE id=:recipeId")
    fun get(recipeId: Int): LiveData<Recipe>

    @Query("SELECT * FROM recipes")
    fun getAll(): LiveData<List<Recipe>>

    @Transaction
    @Query("SELECT * FROM recipes")
    fun getAllWithIngredients(): LiveData<List<RecipeWithIngredients>>

    @Query("SELECT * FROM recipes WHERE isFavorite")
    fun getFavorites(): LiveData<List<Recipe>>

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :recipeId")
    fun setFavorite(recipeId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM recipes WHERE name LIKE :searchRecipe || '%'")
    fun getSearch(searchRecipe: String): LiveData<List<Recipe>>
}
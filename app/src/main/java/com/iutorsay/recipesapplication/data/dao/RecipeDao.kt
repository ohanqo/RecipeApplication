package com.iutorsay.recipesapplication.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.relations.RecipeWithIngredientsAndSteps

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipe: Recipe) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(recipes: List<Recipe>)

    @Query("SELECT * FROM recipes WHERE id=:id")
    fun get(id: Int): LiveData<Recipe>

    @Query("SELECT * FROM recipes")
    fun getAll(): LiveData<List<Recipe>>

    @Transaction
    @Query("SELECT * FROM recipes")
    fun getAllWithIngredientsAndSteps(): LiveData<List<RecipeWithIngredientsAndSteps>>

    @Query("SELECT * FROM recipes WHERE isFavorite")
    fun getFavorites(): LiveData<List<Recipe>>

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :id")
    fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM recipes WHERE name LIKE  '%' || :searchRecipe || '%'")
    fun getSearch(searchRecipe: String): LiveData<List<Recipe>>
}
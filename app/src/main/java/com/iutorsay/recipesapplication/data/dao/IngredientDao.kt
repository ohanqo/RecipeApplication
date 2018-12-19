package com.iutorsay.recipesapplication.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.iutorsay.recipesapplication.data.entities.Ingredient

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredients: List<Ingredient>)

    @Query("SELECT * FROM ingredients WHERE recipe_id = :recipeId")
    fun getRecipeIngredients(recipeId: Int): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredients")
    fun getAll(): LiveData<List<Ingredient>>
}
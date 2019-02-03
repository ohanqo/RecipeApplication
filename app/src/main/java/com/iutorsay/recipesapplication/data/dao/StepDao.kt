package com.iutorsay.recipesapplication.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.iutorsay.recipesapplication.data.entities.Step

@Dao
interface StepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredients: List<Step>)

    @Query("SELECT * FROM instructions WHERE recipe_id = :recipeId")
    fun getRecipeInstructions(recipeId: Int): LiveData<List<Step>>
}
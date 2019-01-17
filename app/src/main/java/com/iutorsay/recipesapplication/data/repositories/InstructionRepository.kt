package com.iutorsay.recipesapplication.data.repositories

import android.os.AsyncTask
import com.iutorsay.recipesapplication.data.AppDatabase
import com.iutorsay.recipesapplication.data.dao.IngredientDao
import com.iutorsay.recipesapplication.data.dao.InstructionDao
import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.data.entities.Instruction
import com.iutorsay.recipesapplication.data.entities.Recipe

class InstructionRepository private constructor(private val databaseInstance: AppDatabase) {
    private val instructionDao = databaseInstance.instructionDao()

    fun getRecipeInstructions(recipeId: Int) = instructionDao.getRecipeInstructions(recipeId)

    fun insertAll(instructions: List<Instruction>) = InsertAsyncTask(instructionDao).execute(instructions)

    companion object {
        @Volatile private lateinit var instance: InstructionRepository

        fun initInstance(databaseInstance: AppDatabase): InstructionRepository {
            instance = InstructionRepository(databaseInstance)
            return instance
        }

        fun getInstance() = instance

        class InsertAsyncTask internal constructor(private val instructionDao: InstructionDao) : AsyncTask<List<Instruction>, Void, Void>() {
            override fun doInBackground(vararg params: List<Instruction>): Void? {
                instructionDao.insertAll(params[0])
                return null
            }
        }
    }
}
package com.iutorsay.recipesapplication.data.repositories

import android.os.AsyncTask
import com.iutorsay.recipesapplication.data.AppDatabase
import com.iutorsay.recipesapplication.data.dao.StepDao
import com.iutorsay.recipesapplication.data.entities.Step

class StepRepository private constructor(private val databaseInstance: AppDatabase) {
    private val instructionDao = databaseInstance.instructionDao()

    fun getRecipeInstructions(recipeId: Int) = instructionDao.getRecipeInstructions(recipeId)

    fun insertAll(steps: List<Step>) = InsertAsyncTask(instructionDao).execute(steps)

    companion object {
        @Volatile private lateinit var instance: StepRepository

        fun initInstance(databaseInstance: AppDatabase): StepRepository {
            instance = StepRepository(databaseInstance)
            return instance
        }

        fun getInstance() = instance

        class InsertAsyncTask internal constructor(private val stepDao: StepDao) : AsyncTask<List<Step>, Void, Void>() {
            override fun doInBackground(vararg params: List<Step>): Void? {
                stepDao.insertAll(params[0])
                return null
            }
        }
    }
}
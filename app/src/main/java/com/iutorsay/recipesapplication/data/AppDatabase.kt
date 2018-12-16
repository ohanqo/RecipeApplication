package com.iutorsay.recipesapplication.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.iutorsay.recipesapplication.data.dao.IngredientDao
import com.iutorsay.recipesapplication.data.dao.RecipeDao
import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.utilities.DATABASE_NAME
import com.iutorsay.recipesapplication.workers.SeedDatabaseWorker

@Database(
    entities = [Recipe::class, Ingredient::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request)
                    }
                })
                .build()
        }
    }
}
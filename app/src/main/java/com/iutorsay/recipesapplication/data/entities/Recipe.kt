package com.iutorsay.recipesapplication.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val recipeId: Int,
    val name: String,
    val description: String,
    val pictureUrl: String,
    var isFavorite: Boolean
) : Serializable {
    @Ignore val ingredients: List<Ingredient> = arrayListOf()
    @Ignore val steps: List<Step> = arrayListOf()
}
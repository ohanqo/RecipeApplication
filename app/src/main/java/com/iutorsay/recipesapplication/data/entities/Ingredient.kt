package com.iutorsay.recipesapplication.data.entities

import android.arch.persistence.room.*
import android.support.annotation.NonNull

@Entity(
    tableName = "ingredients",
    foreignKeys = [ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipe_id"])],
    indices = [Index("recipe_id")]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val ingredientId: Int,
    @ColumnInfo(name = "recipe_id") var recipeId: Int,
    var name: String,
    var quantity: String
)
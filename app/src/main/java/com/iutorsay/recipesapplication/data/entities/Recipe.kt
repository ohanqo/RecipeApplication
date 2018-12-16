package com.iutorsay.recipesapplication.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey @ColumnInfo(name = "id") val recipeId: Int,
    val name: String
) {
    @Ignore val ingredients: List<Ingredient> = arrayListOf()
    //@Ignore val steps: Instruction
}
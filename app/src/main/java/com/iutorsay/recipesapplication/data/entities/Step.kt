package com.iutorsay.recipesapplication.data.entities

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "instructions",
    foreignKeys = [ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipe_id"])],
    indices = [Index("recipe_id")]
)
data class Step(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @SerializedName("id") val instructionId: Int,
    @ColumnInfo(name = "recipe_id") var recipeId: Int,
    var text: String,
    var timing: Int ? = null
)
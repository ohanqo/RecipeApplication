package com.iutorsay.recipesapplication.data.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.entities.Step
import java.io.Serializable
import java.util.*

class RecipeWithIngredientsAndSteps {
    @Embedded
    var recipe: Recipe? = null

    @Relation(parentColumn = "id", entityColumn = "recipe_id")
    var ingredients: List<Ingredient> = ArrayList()

    @Relation(parentColumn = "id", entityColumn = "recipe_id")
    var steps: List<Step> = ArrayList()
}
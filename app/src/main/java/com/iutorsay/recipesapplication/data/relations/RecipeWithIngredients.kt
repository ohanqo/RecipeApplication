package com.iutorsay.recipesapplication.data.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.data.entities.Recipe
import java.util.ArrayList

class RecipeWithIngredients {
    @Embedded
    var recipe: Recipe? = null

    @Relation(parentColumn = "id", entityColumn = "recipe_id")
    var ingredients: List<Ingredient> = ArrayList()
}
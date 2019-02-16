package com.iutorsay.recipesapplication.data.responses

import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.entities.Step

class RecipeResponse {
    var id : Int = 0
    var name : String? = null
    var description : String? = null
    var pictureUrl : String? = null
    var ingredients : List<Ingredient>? = null
    var steps : List<Step>? = null
}
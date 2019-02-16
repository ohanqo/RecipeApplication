package com.iutorsay.recipesapplication.data.services

import com.google.gson.Gson
import com.iutorsay.recipesapplication.BuildConfig.URL_SERVER
import com.iutorsay.recipesapplication.data.responses.RecipeResponse
import com.rx2androidnetworking.Rx2ANRequest
import com.rx2androidnetworking.Rx2AndroidNetworking

class RecipeService {
    companion object {
        fun getRecipes() : Rx2ANRequest {
            return Rx2AndroidNetworking
                .get("$URL_SERVER/recipes")
                .build()
        }

        fun postRecipes(recipesList: List<RecipeResponse>) : Rx2ANRequest {
            val recipesAsJSON = Gson().toJson(recipesList)

            return Rx2AndroidNetworking
                .post("$URL_SERVER/recipes")
                .addHeaders("Content-Type", "application/json")
                .addStringBody(recipesAsJSON)
                .build()
        }
    }
}
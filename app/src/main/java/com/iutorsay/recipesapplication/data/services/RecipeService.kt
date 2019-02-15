package com.iutorsay.recipesapplication.data.services

import com.iutorsay.recipesapplication.BuildConfig.URL_SERVER
import com.rx2androidnetworking.Rx2ANRequest
import com.rx2androidnetworking.Rx2AndroidNetworking

class RecipeService {
    companion object {
        fun getRecipes() : Rx2ANRequest {
            return Rx2AndroidNetworking
                .get("$URL_SERVER/recipes")
                .build()
        }
    }
}
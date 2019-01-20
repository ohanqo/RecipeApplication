package com.iutorsay.recipesapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iutorsay.recipesapplication.data.entities.Recipe
import kotlinx.android.synthetic.main.activity_recipe_detail.*

/**
 * Created by Stephane on 03/01/2019
 */


class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        recipe = intent.getSerializableExtra("recipe") as Recipe

        txtDescription.text = recipe.name
    }
}
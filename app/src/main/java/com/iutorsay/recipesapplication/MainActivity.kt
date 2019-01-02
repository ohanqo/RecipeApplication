package com.iutorsay.recipesapplication

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RecipeRepository.getInstance().getAllWithIngredients().observe(this, Observer { recipesWithIngredients ->
            recipesWithIngredients?.forEach {
                Log.d("—RECIPE", it.recipe?.name)
                Log.d("—INGREDIENTS", it.ingredients.toString())
            }
        })

        val recipes = ArrayList<Recipe>();
        recipes.add(Recipe("1"));
        recipes.add(Recipe("2"));
        recipes.add(Recipe("3"));
        recipes.add(Recipe("4"));
        recipes.add(Recipe("5"));
        recipes.add(Recipe("6"));
        recipes.add(Recipe("7"));
        recipes.add(Recipe("8"));
        recipes.add(Recipe("9"));
        recipes.add(Recipe("10"));
        recipes.add(Recipe("11"));

        //val recyclerView = findViewById(R.id.recyclelistRecipeHomePage) as RecyclerView
        recyclelistRecipeHomePage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclelistRecipeHomePage.adapter = AdapterListHomePage(recipes, this);
    }
}
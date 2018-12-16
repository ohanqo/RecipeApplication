package com.iutorsay.recipesapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById(R.id.recyclelistRecipeHomePage) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

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

        val adapterListRecipe = AdapterListHomePage(recipes);

        recyclerView.adapter = adapterListRecipe;
    }
}

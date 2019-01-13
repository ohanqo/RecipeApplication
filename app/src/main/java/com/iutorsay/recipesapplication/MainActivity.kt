package com.iutorsay.recipesapplication

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.design.widget.AppBarLayout
import android.util.Log
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.toolbar.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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

        recyclelistRecipeHomePage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclelistRecipeHomePage.adapter = AdapterListHomePage(recipes, this);
    }

    fun openRecipeCreationActivity(v: View) {
        startActivity(Intent(this, RecipeCreationActivity::class.java))
    }
}
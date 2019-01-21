package com.iutorsay.recipesapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.iutorsay.recipesapplication.adapters.bindImageFromUrl
import com.iutorsay.recipesapplication.data.entities.Recipe
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.toolbar.view.*

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        recipe = intent.getSerializableExtra("recipe") as Recipe

        toolbar.toolbar_title.text = recipe.name
        bindImageFromUrl(recipe_picture, recipe.pictureUrl)
        recipe_description.text = recipe.description
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
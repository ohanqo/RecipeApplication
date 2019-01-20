package com.iutorsay.recipesapplication

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.iutorsay.recipesapplication.adapters.HomePageAdapter
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import com.iutorsay.recipesapplication.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewmodel.recipes.observe(this, Observer { recipes ->
            recipes?.let {
                recyclelistRecipeHomePage.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter  = HomePageAdapter(recipes)
                }
            }
        })
    }

    fun openRecipeCreationActivity(v: View) {
        startActivity(Intent(this, RecipeCreationActivity::class.java))
    }
}
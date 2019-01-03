package com.iutorsay.recipesapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recipe_detail.*

/**
 * Created by Stephane on 03/01/2019
 */


class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        txtDescription.text = intent.getStringExtra("description");
        supportActionBar?.title = intent.getStringExtra("description");
    }
}
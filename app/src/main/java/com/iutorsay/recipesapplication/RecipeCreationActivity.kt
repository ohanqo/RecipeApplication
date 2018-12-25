package com.iutorsay.recipesapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.iutorsay.recipesapplication.adapters.EditIngredientsAdapter
import com.iutorsay.recipesapplication.adapters.EditInstructionsAdapter
import kotlinx.android.synthetic.main.activity_recipe_creation.*

class RecipeCreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_creation)

        val recipeEditInstructionsAdapter = EditInstructionsAdapter()
        val recipeEditIngredientsAdapter = EditIngredientsAdapter()

        instructionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecipeCreationActivity)
            adapter = recipeEditInstructionsAdapter
        }

        ingredientsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecipeCreationActivity)
            adapter = recipeEditIngredientsAdapter
        }

        addInstructionButton.setOnClickListener {
            recipeEditInstructionsAdapter.addInstruction()
        }

        addIngredientButton.setOnClickListener {
            recipeEditIngredientsAdapter.addIngredient()
        }
    }
}

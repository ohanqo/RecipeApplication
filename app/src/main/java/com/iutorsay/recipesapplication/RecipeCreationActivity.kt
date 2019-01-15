package com.iutorsay.recipesapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.iutorsay.recipesapplication.adapters.EditIngredientsAdapter
import com.iutorsay.recipesapplication.adapters.EditInstructionsAdapter
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import kotlinx.android.synthetic.main.activity_recipe_creation.*
import kotlinx.android.synthetic.main.recipe_image.view.*
import kotlinx.android.synthetic.main.recipe_image_chooser.*
import kotlinx.android.synthetic.main.toolbar.view.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


class RecipeCreationActivity : AppCompatActivity() {

    private lateinit var takePhotoView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_creation)
        setSupportActionBar(toolbar as Toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.recipe_creation)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val recipeEditInstructionsAdapter = EditInstructionsAdapter()
        val recipeEditIngredientsAdapter = EditIngredientsAdapter()

        takePhotoView = layoutInflater.inflate(R.layout.recipe_image_chooser, null)

        recipePictureHolder.addView(takePhotoView)

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

        importPhotoBtn.setOnClickListener {
            EasyImage.openGallery(this, 0)
        }

        takePhotoBtn.setOnClickListener {
            EasyImage.openCamera(this, 0)
        }

        createRecipeButton.setOnClickListener {
            createRecipe()
        }
    }

    private fun createRecipe() {
        if (hasNoErrors()) {
            val index = RecipeRepository.getInstance().insert(Recipe(0, recipeEditTextName.text.toString()))
            Log.d("__CREATERECIPE", "OK")
        }
    }

    private fun hasNoErrors() : Boolean {
        var hasNoError = true

        if (recipeEditTextName.text.isBlank()) {
            recipeEditTextName.error = "Le nom ne peut pas être vide"
            hasNoError = false
        }

        if (recipeEditTextDescription.text.isBlank()) {
            recipeEditTextDescription.error =  "La description ne peut pas être vide"
            hasNoError = false
        }

        if (EditIngredientsAdapter.ingredients.isEmpty()) {
            if (hasNoError) Snackbar.make(recipe_creation, R.string.ingredients_list_empty, Snackbar.LENGTH_SHORT).show()
            hasNoError = false
        }

        if (EditInstructionsAdapter.instructions.isEmpty()) {
            if (hasNoError) Snackbar.make(recipe_creation, R.string.instructions_list_empty, Snackbar.LENGTH_SHORT).show()
            hasNoError = false
        }

        return hasNoError
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
                //TODO: save image on hd
                imageFile?.let { photo ->
                    val bitmapPhoto = BitmapFactory.decodeFile(photo.path)
                    val showPhotoView = layoutInflater.inflate(R.layout.recipe_image, null)
                    showPhotoView.photo.setImageBitmap(bitmapPhoto)
                    showPhotoView.removePhotoBtn.setOnClickListener { removePhoto() }

                    recipePictureHolder.removeAllViews()
                    recipePictureHolder.addView(showPhotoView)
                }
            }

            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }
        })
    }

    private fun removePhoto() {
        recipePictureHolder.removeAllViews()
        recipePictureHolder.addView(takePhotoView)
    }
}

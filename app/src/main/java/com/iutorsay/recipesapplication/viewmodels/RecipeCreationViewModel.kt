package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import com.iutorsay.recipesapplication.MainListFragment
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.entities.Step
import com.iutorsay.recipesapplication.data.repositories.IngredientRepository
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import com.iutorsay.recipesapplication.data.repositories.StepRepository
import com.iutorsay.recipesapplication.utilities.popAllFragments
import com.iutorsay.recipesapplication.utilities.replaceFragment
import java.io.File
import java.io.FileOutputStream

class RecipeCreationViewModel : ViewModel() {
    val currentName = MutableLiveData<String>()

    val currentDescription = MutableLiveData<String>()

    val currentIngredients = ArrayList<Ingredient>()

    val inputIngredientName = MutableLiveData<String>()

    val inputIngredientQuantity = MutableLiveData<String>()

    val currentSteps = ArrayList<Step>()

    val inputStep = MutableLiveData<String>()

    val inputStepTiming = MutableLiveData<String>()

    var recipePhoto : Bitmap? = null

    fun createRecipe(context: AppCompatActivity) {
        val recipe = Recipe(0, currentName.value.toString(), currentDescription.value.toString(), "")
        val index = RecipeRepository.getInstance().insert(recipe).toInt()

        currentIngredients.forEach { ing -> ing.recipeId = index }
        currentSteps.forEach { step -> step.recipeId = index }

        // Clone arraylist because of async insertion that may be done after the clearViewModel method
        val cloneIngredientsList = currentIngredients.toList().map { it.copy() }
        val cloneStepsList = currentSteps.toList().map { it.copy() }

        IngredientRepository.getInstance().insertAll(cloneIngredientsList)
        StepRepository.getInstance().insertAll(cloneStepsList)

        storeRecipePhoto(index, context)

        popAllFragments(context)
        replaceFragment(context, R.id.content, MainListFragment())
        clearViewModel()
    }

    private fun storeRecipePhoto(recipeIndex: Int, context: AppCompatActivity) {
        recipePhoto?.let { photo ->
            val dir = File(context.applicationContext.filesDir, "/recipes/$recipeIndex")
            if (! dir.exists()) dir.mkdirs()
            val filePicture = File(dir, "recipe_photo")
            val fos = FileOutputStream(filePicture)
            photo.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
        }
    }

    private fun clearViewModel() {
        currentName.value = ""
        currentDescription.value = ""
        currentIngredients.clear()
        inputIngredientName.value = ""
        inputIngredientQuantity.value = ""
        currentSteps.clear()
        inputStep.value = ""
        inputStepTiming.value = ""
        recipePhoto = null
    }
}

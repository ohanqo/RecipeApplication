package com.iutorsay.recipesapplication.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.iutorsay.recipesapplication.fragments.HomeFragment
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.entities.Step
import com.iutorsay.recipesapplication.data.relations.RecipeWithIngredientsAndSteps
import com.iutorsay.recipesapplication.data.repositories.IngredientRepository
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import com.iutorsay.recipesapplication.data.repositories.StepRepository
import com.iutorsay.recipesapplication.data.responses.RecipeResponse
import com.iutorsay.recipesapplication.data.services.RecipeService
import com.iutorsay.recipesapplication.utilities.popAllFragments
import com.iutorsay.recipesapplication.utilities.replaceFragment
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import android.R.attr.bitmap
import android.net.Uri
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class CreationViewModel : ViewModel() {

    val currentName = MutableLiveData<String>()

    val currentDescription = MutableLiveData<String>()

    val currentIngredients = ArrayList<Ingredient>()

    val inputIngredientName = MutableLiveData<String>()

    val inputIngredientQuantity = MutableLiveData<String>()

    val currentSteps = ArrayList<Step>()

    val inputStep = MutableLiveData<String>()

    val inputStepTiming = MutableLiveData<String>()

    var recipePhoto : Bitmap? = null

    var recipeResponse : RecipeResponse? = null

    fun createRecipe(context: AppCompatActivity) {
        val recipe = Recipe(0, currentName.value.toString(), currentDescription.value.toString(), "", false)
        val index = RecipeRepository.getInstance().insert(recipe).toInt()

        currentIngredients.forEach { ing -> ing.recipeId = index }
        currentSteps.forEach { step -> step.recipeId = index }

        // Clone arraylist because of async insertion that may be done after the clearViewModel method
        val cloneIngredientsList = currentIngredients.toList().map { it.copy() }
        val cloneStepsList = currentSteps.toList().map { it.copy() }

        IngredientRepository.getInstance().insertAll(cloneIngredientsList)
        StepRepository.getInstance().insertAll(cloneStepsList)

        Log.d("__ADD", "Recette $index ${recipe.name}")

        recipeResponse = RecipeResponse().apply {
            id = index
            name = recipe.name
            description = recipe.description
            pictureUrl = recipe.pictureUrl
            ingredients = cloneIngredientsList
            steps = cloneStepsList
        }

        //postRecipeToApi() Last

        //uploadPhoto(index, context)
        if (recipePhoto != null) {
            storeRecipePhoto(index, context)
        } else {
            postRecipeToApi()
        }

        popAllFragments(context)
        replaceFragment(context, R.id.content, HomeFragment())
        clearViewModel()
    }

    private fun postRecipeToApi() {
        recipeResponse?.let {
            var recipeList : List<RecipeResponse> = ArrayList()
            recipeList += it

            RecipeService.postRecipes(recipeList).getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.d("__RES", response.toString())
                }

                override fun onError(anError: ANError) {
                    Log.d("__ERRR", anError.errorBody.toString())
                }
            })
        }
    }

    private fun uploadPhoto(index: Int, context: AppCompatActivity, filePicture: File) {
            val auth = FirebaseAuth.getInstance()

            auth.signInAnonymously()
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("__TEST", "signInAnonymously:success")
                        val user = auth.currentUser

                        val storage = FirebaseStorage.getInstance()
                        val storageRef = storage.reference
                        val imageRef = storageRef.child("recipes/$index")
                        val baos = ByteArrayOutputStream()
                        recipePhoto?.compress(Bitmap.CompressFormat.JPEG, 50, baos)

                        val uploadTask = imageRef.putFile(Uri.fromFile(filePicture))

                        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw it
                                }
                            }
                            return@Continuation imageRef.downloadUrl
                        }).addOnCompleteListener { taskComplete ->
                            if (taskComplete.isSuccessful) {
                                val downloadUri = taskComplete.result
                                recipeResponse?.pictureUrl = downloadUri.toString()
                                postRecipeToApi()
                                Log.d("__UPLOAD", "OK -> $downloadUri")
                                Toast.makeText(context, "Envoi de l'image effectué", Toast.LENGTH_SHORT).show()
                            } else {
                                postRecipeToApi()
                                Log.d("__UPLOAD", "Error ${taskComplete.exception}")
                            }
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("__TEST", "signInAnonymously:failure", task.exception)
                        postRecipeToApi()
                    }

        }
    }

    private fun storeRecipePhoto(recipeIndex: Int, context: AppCompatActivity) {
        recipePhoto?.let { photo ->
            val dir = File(context.applicationContext.filesDir, "/recipes/$recipeIndex")
            if (! dir.exists()) dir.mkdirs()
            val filePicture = File(dir, "recipe_photo")
            val fos = FileOutputStream(filePicture)
            photo.compress(Bitmap.CompressFormat.PNG, 50, fos)
            uploadPhoto(recipeIndex, context, filePicture)
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

package com.iutorsay.recipesapplication

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.google.gson.reflect.TypeToken
import com.iutorsay.recipesapplication.data.relations.RecipeWithIngredientsAndSteps
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import com.iutorsay.recipesapplication.data.responses.RecipeResponse
import com.iutorsay.recipesapplication.data.services.RecipeService
import com.iutorsay.recipesapplication.fragments.HomeFragment
import com.iutorsay.recipesapplication.fragments.LibraryFragment
import com.iutorsay.recipesapplication.fragments.SearchFragment
import com.iutorsay.recipesapplication.utilities.replaceFragmentWithoutBackStack
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            toolbar.toolbar_title.typeface = resources.getFont(R.font.circular_std_bold)
        }

        handleNavigationClick()

        showFragment(HomeFragment())

        RecipeService
            .getRecipes()
            .getAsParsed(
                object: TypeToken<List<RecipeResponse>>() {},
                object: ParsedRequestListener<List<RecipeResponse>> {
                    override fun onResponse(response: List<RecipeResponse>) {
                        response.map { recipe ->
                            Log.d("__REC", recipe.name.toString())
                            Log.d("__ING", recipe.ingredients.toString())
                            Log.d("__STE", recipe.steps.toString())
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("__ERREUR", "Récuperation des recettes via l'API impossibles $anError")
                    }
                }
            )
    }

    private fun handleNavigationClick() {
        navigation.setOnNavigationItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.home -> {
                    showFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    showFragment(SearchFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.library -> {
                    showFragment(LibraryFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun showFragment(fragment: Fragment) {
        replaceFragmentWithoutBackStack(this as AppCompatActivity, R.id.content, fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> this.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
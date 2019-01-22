package com.iutorsay.recipesapplication.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.RecipeDetailActivity
import com.iutorsay.recipesapplication.RecipeDetailFragment
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.databinding.RecipeCardBinding
import android.support.v7.app.AppCompatActivity

class HomePageAdapter(private val context: Context, private val userRecipeList: List<Recipe>) : RecyclerView.Adapter<HomePageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<RecipeCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recipe_card, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount() = userRecipeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = userRecipeList[position]

        with(holder) {
            bind(recipe)
        }


        holder.itemView.setOnClickListener {
            //val intent = Intent(it.context, RecipeDetailActivity::class.java)
            //intent.putExtra("recipe", recipe)
            //it.context.startActivity(intent)
            val manager = (context as AppCompatActivity).supportFragmentManager
            val detailFragment = RecipeDetailFragment()
            val bundle = Bundle()

            bundle.putSerializable("recipe", recipe)
            detailFragment.arguments = bundle

            manager.beginTransaction()
                .replace(R.id.content, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    inner class ViewHolder(private val binding: RecipeCardBinding, var recipe: Recipe? = null) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(_recipe: Recipe) {
            with(binding) {
                recipe = _recipe
                executePendingBindings()
            }
        }
    }
}

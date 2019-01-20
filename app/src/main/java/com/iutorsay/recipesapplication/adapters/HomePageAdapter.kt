package com.iutorsay.recipesapplication.adapters

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.MainActivity
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.RecipeDetailActivity
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.databinding.RecipeCardBinding
import kotlinx.android.synthetic.main.recipe_card.view.*

class HomePageAdapter(private val userRecipeList: List<Recipe>) : RecyclerView.Adapter<HomePageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
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
            val intent = Intent(it.context, RecipeDetailActivity::class.java)
            intent.putExtra("recipe", recipe)
            it.context.startActivity(intent)
        }
    }

    inner class ViewHolder(private val binding: RecipeCardBinding, var recipe: Recipe? = null) : RecyclerView.ViewHolder(binding.root) {
        fun bind(_recipe: Recipe) {
            with(binding) {
                recipe = _recipe
                executePendingBindings()
            }
        }
    }
}

package com.iutorsay.recipesapplication.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.RecipeDetailActivity
import com.iutorsay.recipesapplication.data.entities.Recipe
import kotlinx.android.synthetic.main.list_item_home.view.*

/**
 * Created by Stephane on 16/12/2018
 */

class HomePageAdapter(private val userRecipeList: List<Recipe>) : RecyclerView.Adapter<HomePageAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, var recipe: Recipe? = null) : RecyclerView.ViewHolder(itemView) {
        val txtNameItem = itemView.itemListHomeName

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, RecipeDetailActivity::class.java);
                intent.putExtra("description", recipe?.name)
                itemView.context.startActivity(intent);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_home, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = userRecipeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemRecipe: Recipe = userRecipeList[position]
        holder.recipe = itemRecipe
        holder.txtNameItem.text = itemRecipe.name
    }
}

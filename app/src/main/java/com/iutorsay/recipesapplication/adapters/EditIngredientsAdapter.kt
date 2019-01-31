package com.iutorsay.recipesapplication.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Ingredient
import kotlinx.android.synthetic.main.card_ingredient.view.*

class EditIngredientsAdapter(private val clickListener: (Ingredient) -> Unit) : RecyclerView.Adapter<EditIngredientsAdapter.IngredientHolder>() {
    private var ingredients: List<Ingredient> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditIngredientsAdapter.IngredientHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_ingredient, parent, false)
        return IngredientHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val currentIngredient = ingredients[position]
        holder.ingredientNameTextView.text = currentIngredient.name
        holder.ingredientQuantityTextView.text = currentIngredient.quantity
        (holder).bind(currentIngredient, clickListener)
    }

    override fun getItemCount(): Int = ingredients.size

    fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    inner class IngredientHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ingredientNameTextView: TextView = itemView.ingredient_name
        val ingredientQuantityTextView: TextView = itemView.ingredient_quantity

        fun bind(ingredient: Ingredient, clickListener: (Ingredient) -> Unit) {
            itemView.close.setOnClickListener { clickListener(ingredient) }
        }
    }
}

package com.iutorsay.recipesapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Ingredient
import kotlinx.android.synthetic.main.ingredient_detail.view.*

class DetailIngredientAdapter(private val ingredientList: List<Ingredient>) : RecyclerView.Adapter<DetailIngredientAdapter.IngredientHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_detail, parent, false)
        return IngredientHolder(itemView)
    }

    override fun getItemCount()  = ingredientList.size

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val currentIngredient = ingredientList[position]
        holder.ingredientName.text = currentIngredient.name
        holder.ingredientQuantity.text = currentIngredient.quantity
    }

    inner class IngredientHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ingredientName : TextView = itemView.ingredient_name
        var ingredientQuantity : TextView = itemView.ingredient_quantity
    }
}
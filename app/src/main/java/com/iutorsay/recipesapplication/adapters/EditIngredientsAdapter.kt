package com.iutorsay.recipesapplication.adapters

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Ingredient
import kotlinx.android.synthetic.main.recipe_ingredient_edit_card.view.*

class EditIngredientsAdapter : RecyclerView.Adapter<EditIngredientsAdapter.IngredientHolder>() {
    companion object {
        var ingredients: List<Ingredient> = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_ingredient_edit_card, parent, false)
        return IngredientHolder(itemView)
    }

    override fun getItemCount() = ingredients.size

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val currentIngredient = ingredients[position]
        holder.ingredientNameTextListener.updatePosition(holder.adapterPosition)
        holder.ingredientQuantityTextListener.updatePosition(holder.adapterPosition)
        holder.ingredientName.setText(currentIngredient.name)
        holder.ingredientQuantity.setText(currentIngredient.quantity)
        if (position == itemCount - 1) holder.ingredientName.requestFocus()
    }

    fun addIngredient() {
        ingredients += Ingredient(0, 0, "", "")
        notifyDataSetChanged()
    }

    inner class IngredientHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deleteIngredient: Button = itemView.deleteIngredient
        var ingredientName: EditText = itemView.editTextIngredient
        var ingredientQuantity: EditText = itemView.editTextQuantity
        val ingredientNameTextListener = IngredientTextListener(ingredientName)
        val ingredientQuantityTextListener = IngredientTextListener(ingredientQuantity)

        init {
            ingredientName.addTextChangedListener(ingredientNameTextListener)
            ingredientQuantity.addTextChangedListener(ingredientQuantityTextListener)
            deleteIngredient.setOnClickListener {
                ingredients -= ingredients[adapterPosition]
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    inner class IngredientTextListener(var editText: EditText) : TextWatcher {
        private var position: Int? = null

        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            position?.let {
                when (editText.id) {
                    R.id.editTextIngredient -> ingredients[it].name = s.toString()
                    else -> ingredients[it].quantity = s.toString()
                }
            }
        }
    }
}

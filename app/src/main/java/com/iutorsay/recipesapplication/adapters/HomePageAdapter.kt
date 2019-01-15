package com.iutorsay.recipesapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iutorsay.recipesapplication.data.entities.Recipe
import kotlinx.android.synthetic.main.list_item_home.view.*

/**
 * Created by Stephane on 16/12/2018
 */

class AdapterListHomePage(private val userRecipeList: List<Recipe>) : RecyclerView.Adapter<AdapterListHomePage.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtNameItem = itemView.itemListHomeName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //? pour gerer le null
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_home, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //retourne le nombre d'elements
        return userRecipeList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val itemRecipe: Recipe = userRecipeList[p1]

        p0.txtNameItem.text = "Coucou"
    }


}
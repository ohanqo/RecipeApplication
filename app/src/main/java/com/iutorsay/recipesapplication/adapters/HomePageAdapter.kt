package com.iutorsay.recipesapplication.adapters

import android.animation.Animator
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.fragments.DetailFragment
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import com.iutorsay.recipesapplication.databinding.CardRecipeBinding
import com.iutorsay.recipesapplication.utilities.replaceFragment
import kotlinx.android.synthetic.main.card_recipe.view.*

class HomePageAdapter(private val context: Context) : ListAdapter<Recipe, HomePageAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<CardRecipeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.card_recipe, parent, false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)

        with(holder) {
            bind(recipe)
        }

        if (recipe.isFavorite) {
            holder.itemView.heart.progress = 1f
        } else {
            holder.itemView.heart.progress = 0f
        }

        handleFavoriteClick(holder, recipe)

        handleCardClick(holder, recipe)
    }

    private fun handleFavoriteClick(holder: ViewHolder, recipe: Recipe) {
        holder.itemView.heart.setOnClickListener {

            // Avoid spam by removing listener during the animation
            holder.itemView.heart.setOnClickListener(null)

            if (recipe.isFavorite) {
                RecipeRepository.getInstance().setFavorites(recipe.recipeId, false)
                holder.itemView.announceForAccessibility("Recette retirée des favoris")
            } else {
                holder.itemView.heart.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        RecipeRepository.getInstance().setFavorites(recipe.recipeId, true)
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })

                holder.itemView.heart.playAnimation()

                holder.itemView.announceForAccessibility("Recette ajoutée aux favoris")
            }
        }
    }

    private fun handleCardClick(holder: ViewHolder, recipe: Recipe) {
        holder.itemView.setOnClickListener {
            val detailFragment = DetailFragment()
            val bundle = Bundle()

            bundle.putSerializable("recipe", recipe)
            detailFragment.arguments = bundle

            replaceFragment(context as AppCompatActivity, R.id.content, detailFragment)
        }
    }

    inner class ViewHolder(private val binding: CardRecipeBinding, var recipe: Recipe? = null) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(_recipe: Recipe) {
            with(binding) {
                recipe = _recipe
                executePendingBindings()
            }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.recipeId == newItem.recipeId
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.name.equals(newItem.name) &&
                        oldItem.description.equals(newItem.description) &&
                        oldItem.ingredients == newItem.ingredients &&
                        oldItem.steps == newItem.steps &&
                        oldItem.isFavorite == newItem.isFavorite
            }
        }
    }
}

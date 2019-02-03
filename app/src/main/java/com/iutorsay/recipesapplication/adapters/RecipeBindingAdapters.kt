package com.iutorsay.recipesapplication.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.iutorsay.recipesapplication.data.entities.Recipe
import java.io.File

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, recipe: Recipe) {
    Glide.with(view.context).clear(view) // Avoid loading wrong image

    if (!recipe.pictureUrl.isEmpty()) {
        Glide.with(view.context)
            .load(recipe.pictureUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    } else {
        val photo = File("${view.context.applicationContext.filesDir}/recipes/${recipe.recipeId}/recipe_photo")
        if (photo.exists()) {
            Glide.with(view.context)
                .load(photo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}
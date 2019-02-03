package com.iutorsay.recipesapplication.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Recipe
import java.io.File

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, recipe: Recipe) {
    Glide.with(view.context).clear(view) // Avoid loading wrong image

    if (!recipe.pictureUrl.isEmpty()) {
        loadImage(view, recipe.pictureUrl)
    } else {
        val photo = File("${view.context.applicationContext.filesDir}/recipes/${recipe.recipeId}/recipe_photo")
        if (photo.exists()) {
            loadImage(view, photo)
        } else {
            loadImage(view, R.mipmap.no_picture)
        }
    }
}

private fun loadImage(view: ImageView, image: Any) {
    Glide.with(view.context)
        .load(image)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}
package com.iutorsay.recipesapplication.fragments.creation

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.viewmodels.RecipeCreationViewModel
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.recipe_image.view.*
import kotlinx.android.synthetic.main.recipe_image_chooser.*
import kotlinx.android.synthetic.main.recipe_image_chooser.view.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class PhotoFragment : Fragment() {
    private lateinit var creationViewModel: RecipeCreationViewModel
    private lateinit var takePhotoView: View
    private lateinit var showPhotoView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creationViewModel = activity?.run {
            ViewModelProviders.of(this).get(RecipeCreationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        takePhotoView = layoutInflater.inflate(R.layout.recipe_image_chooser, null)
        showPhotoView = layoutInflater.inflate(R.layout.recipe_image, null)

        takePhotoView.importPhotoBtn.setOnClickListener {
            EasyImage.openGallery(this, 0)
        }

        takePhotoView.takePhotoBtn.setOnClickListener {
            EasyImage.openCamera(this, 0)
        }

        if (creationViewModel.recipePhoto == null) {
            picture_holder.addView(takePhotoView)
        } else {
            displayPhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, object : DefaultCallback() {
            override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
                imageFile?.let { photo ->
                    creationViewModel.recipePhoto = BitmapFactory.decodeFile(photo.path)
                    displayPhoto()
                }
            }

            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                Toast.makeText(context, resources.getText(R.string.error_photo), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayPhoto() {
        showPhotoView.photo.setImageBitmap(creationViewModel.recipePhoto)
        showPhotoView.removePhotoBtn.setOnClickListener { removePhoto() }

        picture_holder.removeAllViews()
        picture_holder.addView(showPhotoView)
    }

    private fun removePhoto() {
        picture_holder.removeAllViews()
        picture_holder.addView(takePhotoView)
    }
}
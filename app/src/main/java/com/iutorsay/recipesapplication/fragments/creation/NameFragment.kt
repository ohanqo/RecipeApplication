package com.iutorsay.recipesapplication.fragments.creation

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.MainActivity
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.databinding.FragmentNameBinding
import com.iutorsay.recipesapplication.utilities.addFragment
import com.iutorsay.recipesapplication.viewmodels.RecipeCreationViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_name.*

class NameFragment : Fragment() {
    private lateinit var creationViewModel: RecipeCreationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creationViewModel = activity?.run {
            ViewModelProviders.of(this).get(RecipeCreationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val binding = DataBindingUtil.inflate<FragmentNameBinding>(inflater, R.layout.fragment_name, container, false).apply {
            viewmodel = creationViewModel
            setLifecycleOwner(this@NameFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).toolbar.toolbar_title.text = resources.getString(R.string.recipe_creation)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        button_next.setOnClickListener { handleButtonClick() }
    }

    private fun handleButtonClick() {
        if (hasNoErrors()) {
            addFragment(context as AppCompatActivity, R.id.content, IngredientsFragment())
        }
    }

    private fun hasNoErrors(): Boolean {
        var hasNoErrors = true

        if (creationViewModel.currentName.value.isNullOrBlank()) {
            input_step.error = "Le nom ne peut pas être vide"
            hasNoErrors = false
        }

        if (creationViewModel.currentDescription.value.isNullOrBlank()) {
            input_description.error = "La description ne peut pas être vide"
            hasNoErrors = false
        }

        return hasNoErrors
    }
}

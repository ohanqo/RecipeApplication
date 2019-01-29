package com.iutorsay.recipesapplication

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.viewmodels.RecipeCreationViewModel


class RecipeCreationFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeCreationFragment()
    }

    private lateinit var viewModel: RecipeCreationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_creation_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeCreationViewModel::class.java)
    }

}

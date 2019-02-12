package com.iutorsay.recipesapplication.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.MainActivity
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.adapters.HomePageAdapter
import com.iutorsay.recipesapplication.fragments.creation.NameFragment
import com.iutorsay.recipesapplication.utilities.replaceFragment
import com.iutorsay.recipesapplication.viewmodels.LibraryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment : Fragment() {
    private lateinit var viewModel: LibraryViewModel
    private lateinit var homeAdapter: HomePageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).toolbar.toolbar_title.text = resources.getString(R.string.menu_library)

        viewModel = ViewModelProviders.of(this).get(LibraryViewModel::class.java)

        homeAdapter = HomePageAdapter(activity!!)

        fav_recipes_recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
            setHasFixedSize(true)
        }

        button_create_recipe.setOnClickListener {
            replaceFragment(context as AppCompatActivity, R.id.content, NameFragment())
        }

        viewModel.favRecipes.observe(this, Observer { recipes ->
            recipes?.let {
                Log.d("__TASK", recipes.toString())
                homeAdapter.submitList(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar.toolbar_title.text = resources.getString(R.string.menu_library)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}
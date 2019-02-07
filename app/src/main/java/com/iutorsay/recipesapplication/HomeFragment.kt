package com.iutorsay.recipesapplication

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.adapters.HomePageAdapter
import com.iutorsay.recipesapplication.viewmodels.MainListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.main_list_fragment.*


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: MainListViewModel
    private lateinit var homeAdapter: HomePageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)

        viewModel = ViewModelProviders.of(this).get(MainListViewModel::class.java)

        homeAdapter = HomePageAdapter(activity!!)

        main_recipe_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter  = homeAdapter
            setHasFixedSize(true)
        }

        viewModel.recipes.observe(this, Observer { recipes ->
            recipes?.let {
                homeAdapter.submitList(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}

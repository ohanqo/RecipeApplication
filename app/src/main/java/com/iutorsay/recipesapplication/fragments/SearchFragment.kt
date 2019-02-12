package com.iutorsay.recipesapplication.fragments

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.MainActivity
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.adapters.HomePageAdapter
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.repositories.RecipeRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private var list : LiveData<List<Recipe>>? = null
    private lateinit var homeAdapter: HomePageAdapter

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).toolbar.toolbar_title.text = resources.getString(R.string.search_title)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)

        val search = RecipeRepository.getInstance();
        list = search.getAll();

        searchInput.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                list = search.getSearch(searchInput.text.toString());
                rechargeList(list);
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        homeAdapter = HomePageAdapter(activity!!)

        search_recycler_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter  = homeAdapter
            setHasFixedSize(true)
        }

        list?.observe(this, Observer { recipes ->
            recipes?.let {
                homeAdapter.submitList(it)
            }
        })
    }

    fun rechargeList(list: LiveData<List<Recipe>>?){

        list?.observe(this, Observer { recipes ->
            recipes?.let {
                homeAdapter.submitList(it)
            }
        })


        /*list?.observe(this, Observer { recipes ->
            recipes?.let {
                search_recycler_list.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter  = HomePageAdapter(activity!!, recipes)
                    setHasFixedSize(true)
                }
            }
        })*/
    }
}

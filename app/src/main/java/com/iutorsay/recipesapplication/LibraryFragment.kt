package com.iutorsay.recipesapplication

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.fragments.creation.NameFragment
import com.iutorsay.recipesapplication.utilities.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment : Fragment() {
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

        button_create_recipe.setOnClickListener {
            replaceFragment(context as AppCompatActivity, R.id.content, NameFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar.toolbar_title.text = resources.getString(R.string.menu_library)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}
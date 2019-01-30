package com.iutorsay.recipesapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        handleNavigationClick()

        showFragment(MainListFragment())
    }

    private fun handleNavigationClick() {
        navigation.setOnNavigationItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.home -> {
                    showFragment(MainListFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> this.onBackPressed()
        }

        toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        return super.onOptionsItemSelected(item)
    }
}
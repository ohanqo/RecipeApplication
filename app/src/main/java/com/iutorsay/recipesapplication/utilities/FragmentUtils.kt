package com.iutorsay.recipesapplication.utilities

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun replaceFragment(context: AppCompatActivity, viewIdentifier: Int, fragment: Fragment) {
    val manager = context.supportFragmentManager
    manager.beginTransaction()
        .replace(viewIdentifier, fragment)
        .addToBackStack(null)
        .commit()
}
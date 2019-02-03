package com.iutorsay.recipesapplication.utilities

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v7.app.AppCompatActivity

fun replaceFragment(context: AppCompatActivity, viewIdentifier: Int, fragment: Fragment) {
    val manager = context.supportFragmentManager
    manager.beginTransaction()
        .replace(viewIdentifier, fragment)
        .addToBackStack(null)
        .commit()
}

fun replaceFragmentWithoutBackStack(context: AppCompatActivity, viewIdentifier: Int, fragment: Fragment) {
    val manager = context.supportFragmentManager
    manager.beginTransaction()
        .replace(viewIdentifier, fragment)
        .commit()
}

fun addFragment(context: AppCompatActivity, viewIdentifier: Int, fragment: Fragment) {
    val manager = context.supportFragmentManager
    manager.beginTransaction()
        .add(viewIdentifier, fragment)
        .addToBackStack(null)
        .commit()
}

fun popAllFragments(context: AppCompatActivity) {
    context.supportFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
}
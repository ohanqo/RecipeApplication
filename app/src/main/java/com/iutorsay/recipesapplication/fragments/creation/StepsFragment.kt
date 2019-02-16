package com.iutorsay.recipesapplication.fragments.creation

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.adapters.EditStepsAdapter
import com.iutorsay.recipesapplication.data.entities.Step
import com.iutorsay.recipesapplication.databinding.FragmentStepsBinding
import com.iutorsay.recipesapplication.utilities.addFragment
import com.iutorsay.recipesapplication.utilities.hideSoftKeyboard
import com.iutorsay.recipesapplication.viewmodels.CreationViewModel
import kotlinx.android.synthetic.main.fragment_steps.*

class StepsFragment : Fragment() {
    private lateinit var creationViewModel: CreationViewModel
    private lateinit var editStepsAdapter: EditStepsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creationViewModel = activity?.run {
            ViewModelProviders.of(this).get(CreationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val binding = DataBindingUtil.inflate<FragmentStepsBinding>(inflater, R.layout.fragment_steps, container, false).apply {
            viewmodel = creationViewModel
            setLifecycleOwner(this@StepsFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editStepsAdapter = EditStepsAdapter() { step: Step -> onStepCloseButtonClick(step) }

        steps_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = editStepsAdapter
            setHasFixedSize(true)
        }

        editStepsAdapter.setSteps(creationViewModel.currentSteps)

        button_add_step.setOnClickListener {
            activity?.let { _activity -> hideSoftKeyboard(_activity) }
            addStepToList()
        }

        button_next.setOnClickListener {
            activity?.let { _activity -> hideSoftKeyboard(_activity) }

            if (creationViewModel.currentSteps.size > 0) {
                addFragment(context as AppCompatActivity, R.id.content, PhotoFragment())
            } else {
                Toast.makeText(context, resources.getText(R.string.instructions_list_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onStepCloseButtonClick(step: Step) {
        creationViewModel.currentSteps.remove(step)
        editStepsAdapter.setSteps(creationViewModel.currentSteps)
    }

    private fun addStepToList() {
        if (hasNoErrors()) {
            val stepToAdd = Step(0, 0, creationViewModel.inputStep.value!!, creationViewModel.inputStepTiming.value?.toIntOrNull())
            creationViewModel.currentSteps.add(stepToAdd)
            editStepsAdapter.setSteps(creationViewModel.currentSteps)
            creationViewModel.inputStep.value = ""
            creationViewModel.inputStepTiming.value = null
        }
    }

    private fun hasNoErrors(): Boolean {
        var hasNoErrors = true

        if (creationViewModel.inputStep.value.isNullOrBlank()) {
            hasNoErrors = false
            input_step.error = "Vous devez indiquer une étape"
        }

        return hasNoErrors
    }
}
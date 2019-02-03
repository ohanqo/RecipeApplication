package com.iutorsay.recipesapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Step
import kotlinx.android.synthetic.main.step_detail.view.*

class DetailStepAdapter(private val ingredientList: List<Step>) : RecyclerView.Adapter<DetailStepAdapter.StepHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.step_detail, parent, false)
        return StepHolder(itemView)
    }

    override fun getItemCount()  = ingredientList.size

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        val currentStep = ingredientList[position]
        holder.step.text = currentStep.text
        currentStep.timing?.let {
            holder.stepTiming.text = "Durée: ${currentStep.timing} mn"
        }
    }

    inner class StepHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var step : TextView = itemView.step
        var stepTiming : TextView = itemView.step_timing
    }
}
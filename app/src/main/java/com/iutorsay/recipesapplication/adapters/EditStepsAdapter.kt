package com.iutorsay.recipesapplication.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Step
import kotlinx.android.synthetic.main.card_step.view.*

class EditStepsAdapter(private val clickListener: (Step) -> Unit) : RecyclerView.Adapter<EditStepsAdapter.StepHolder>() {
    private var steps: List<Step> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditStepsAdapter.StepHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_step, parent, false)
        return StepHolder(itemView)
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        val currentStep = steps[position]
        holder.stepTextView.text = currentStep.text
        Log.d("__TIMING", currentStep.timing.toString())
        currentStep.timing?.let {
            holder.stepTimingTextView.text = "Durée:  $it"
        }
        (holder).bind(currentStep, clickListener)
    }

    override fun getItemCount(): Int = steps.size

    fun setSteps(steps: List<Step>) {
        this.steps = steps
        notifyDataSetChanged()
    }

    inner class StepHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val stepTextView: TextView = itemView.step
        val stepTimingTextView: TextView = itemView.step_timing

        fun bind(step: Step, clickListener: (Step) -> Unit) {
            itemView.close.setOnClickListener { clickListener(step) }
        }
    }
}
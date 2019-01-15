package com.iutorsay.recipesapplication.adapters

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.data.entities.Instruction
import kotlinx.android.synthetic.main.recipe_instruction_edit_card.view.*
import java.util.ArrayList


class EditInstructionsAdapter : RecyclerView.Adapter<EditInstructionsAdapter.InstructionsHolder>() {
    companion object {
        var instructions: List<Instruction> = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_instruction_edit_card, parent, false)
        return InstructionsHolder(itemView)
    }

    override fun getItemCount() = instructions.size

    override fun onBindViewHolder(holder: InstructionsHolder, position: Int) {
        val currentInstruction = instructions[position]
        holder.instructionTextListener.updatePosition(holder.adapterPosition)
        holder.instructionText.setText(currentInstruction.text)
        if (position == itemCount - 1) holder.instructionText.requestFocus()
    }

    fun addInstruction() {
        instructions += Instruction(0, "")
        notifyDataSetChanged()
    }

    inner class InstructionsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deleteInstruction: Button = itemView.deleteIngredient
        val instructionTextListener = InstructionTextListener()
        var instructionText: EditText = itemView.editTextIngredient

        init {
            instructionText.addTextChangedListener(instructionTextListener)
            deleteInstruction.setOnClickListener {
                instructions -= instructions[adapterPosition]
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    inner class InstructionTextListener : TextWatcher {
        private var position: Int? = null

        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            position?.let { instructions[it].text = s.toString() }
        }
    }
}

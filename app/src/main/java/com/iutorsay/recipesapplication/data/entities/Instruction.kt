package com.iutorsay.recipesapplication.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "instructions")
data class Instruction(
    @PrimaryKey @ColumnInfo(name = "id") val instructionId: Int,
    var text: String
)
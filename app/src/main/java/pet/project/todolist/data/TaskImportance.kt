package pet.project.todolist.data

import androidx.annotation.StringRes
import pet.project.todolist.R

enum class TaskImportance(@StringRes val importanceString: Int) {
    LOW(R.string.Low), DEFAULT(R.string.Default), HIGH(R.string.High)
}

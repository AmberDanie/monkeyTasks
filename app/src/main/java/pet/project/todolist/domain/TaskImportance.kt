package pet.project.todolist.domain

import androidx.annotation.StringRes
import pet.project.todolist.R

/**
 * TaskImportance represents Importance states
 * */

enum class TaskImportance(@StringRes val importanceString: Int) {
    LOW(R.string.Low), DEFAULT(R.string.Default), HIGH(R.string.High)
}

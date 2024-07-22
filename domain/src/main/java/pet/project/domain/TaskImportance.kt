package pet.project.domain

import androidx.annotation.StringRes

/**
 * TaskImportance represents Importance states
 * */

enum class TaskImportance(@StringRes val importanceString: Int) {
    LOW(R.string.Low), DEFAULT(R.string.Default), HIGH(R.string.High)
}

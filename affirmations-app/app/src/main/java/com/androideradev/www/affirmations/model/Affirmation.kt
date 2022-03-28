package com.androideradev.www.affirmations.model

import androidx.annotation.DrawableRes

data class Affirmation(

    val affirmationText: String, @DrawableRes val imageResourceId: Int) {
}
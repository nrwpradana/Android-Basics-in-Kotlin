package com.androideradev.www.affirmations.data

import android.content.Context
import com.androideradev.www.affirmations.R
import com.androideradev.www.affirmations.model.Affirmation

object DataSource {

    fun loadAffirmations(context: Context): List<Affirmation> {
        val affirmationText = context
            .resources
            .getStringArray(R.array.affirmations)

        val affirmationImages = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
        )

        return affirmationText.zip(affirmationImages) { text, imageId ->
            Affirmation(text, imageId)
        }


    }


}
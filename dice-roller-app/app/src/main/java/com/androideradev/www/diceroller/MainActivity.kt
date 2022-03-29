package com.androideradev.www.diceroller

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


private const val ROLL_DICE_RESOURCE_KEY = "roll_dice_resource_key"
private const val SECOND_ROLL_DICE_RESOURCE_KEY = "second_roll_dice_resource_key"

class MainActivity : AppCompatActivity() {


    private var diceDrawableResource = R.drawable.dice_1
    private var secondDiceDrawableResource = R.drawable.dice_2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton = findViewById<Button>(R.id.roll_button)
        val diceImageView = findViewById<ImageView>(R.id.dice_image_view)
        val secondDiceImageView = findViewById<ImageView>(R.id.dice_image_view_second)

        // Check if savedInstanceState not null to get the saved image resource ID from the bundle
        diceDrawableResource =
            savedInstanceState?.getInt(ROLL_DICE_RESOURCE_KEY)
                ?: getDiceDrawableResource(rollDice())
        secondDiceDrawableResource =
            savedInstanceState?.getInt(SECOND_ROLL_DICE_RESOURCE_KEY)
                ?: getDiceDrawableResource(rollDice())

        diceImageView.setImageResource(diceDrawableResource)
        secondDiceImageView.setImageResource(secondDiceDrawableResource)

        rollButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                diceDrawableResource = getDiceDrawableResource(rollDice())
                diceImageView.setImageResource(diceDrawableResource)
                secondDiceDrawableResource = getDiceDrawableResource(rollDice())
                secondDiceImageView.setImageResource(secondDiceDrawableResource)


            }
        })
    }

    /**
     * Roll dice and update the screen with the result.
     */
    private fun rollDice(): Int {
        // Create new Dice object with 6 sides and roll the dice
        val dice = Dice()
        return dice.roll()
    }

    private fun getDiceDrawableResource(diceRoll: Int): Int = when (diceRoll) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the image dice resource ID when the user rotate the device
        outState.putInt(ROLL_DICE_RESOURCE_KEY, diceDrawableResource)
        outState.putInt(SECOND_ROLL_DICE_RESOURCE_KEY, secondDiceDrawableResource)
    }
}


class Dice(private val numSides: Int = 6) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
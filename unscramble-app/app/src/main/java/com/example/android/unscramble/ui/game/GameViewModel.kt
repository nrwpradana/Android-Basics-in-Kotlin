package com.example.android.unscramble.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // initialize to 0
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score
    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int> get() = _currentWordCount

    // val because the value of the LiveData/MutableLiveData object will remain the same,
    // and only the data stored within the object will change.
    private val _currentScrambledWord = MutableLiveData<String>()
    /*A better user experience would be to have Talkback read aloud the individual characters of the scrambled word.
    Within the GameViewModel, convert the scrambled word String to a Spannable string.
    A spannable string is a string with some extra information attached to it. In this case,
    we want to associate the string with a TtsSpan of TYPE_VERBATIM,
    so that the text-to-speech engine reads aloud the scrambled word verbatim, character by character.*/
    val currentScrambledWord: LiveData<Spannable> =
        Transformations.map(_currentScrambledWord) {
            if (it == null) {
                SpannableString("")
            } else {
                val scrambledWord = it.toString()
                val spannable: Spannable = SpannableString(scrambledWord)
                spannable.setSpan(
                    TtsSpan.VerbatimBuilder(scrambledWord).build(),
                    0,
                    scrambledWord.length,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                spannable
            }

        }

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        // Call getNextWord() to initialize the late init properties (_currentScrambledWord, currentWord)
        // while the view model instance is created
        getNextWord()
    }


    /*
     * Updates currentWord and currentScrambledWord with the next word.
     */
    fun getNextWord() {
        // Get random word from the words list
        currentWord = allWordsList.random()
        // Convert the word string to characters array
        val tempWord = currentWord.toCharArray()
        // keep shuffling the word characters if it's as the original word
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            // If the word already exists in the wordsList which tracks the used words
            // Call getNextWork method to generate another random word from the original words list
            getNextWord()
        } else {
            // The word not used before assign it to the _currentScrambledWord
            _currentScrambledWord.value = String(tempWord)
            // Increase the currentWordCount
            _currentWordCount.value = _currentWordCount.value?.inc() // increment the value by one
            // Add the world to tracking list
            wordsList.add(currentWord)
        }
    }

    /* Returns true if the current word count is less than MAX_NO_OF_WORDS.
     Updates the next word.
     */
    fun nextWord() = if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
        getNextWord()
        true
    } else false


    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    /*Helper method to validate player word*/
    fun isUserWordCorrect(playerWord: String): Boolean {
        //  Validate the player's word and increase the score if the guess is correct.
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializedData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
}
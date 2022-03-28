package com.androideradev.www.affirmations

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AffirmationsListTests {

    @get:Rule
    val mainActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun scroll_to_test() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val affirmations = context
            .resources
            .getStringArray(R.array.affirmations)
        val affirmationText = affirmations[affirmations.lastIndex]
        // When you are unsure of the length of your list
        // you can use the scrollTo action. The scrollTo function
        // requires a ViewMatcher to find a particular item
        onView(withId(R.id.affirmation_recycler_view)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(affirmationText))
            )
        )
        // Check if Recycler View list item with text affirmationText is currently
        // displayed on the screen
        onView(withText(affirmationText)).check(matches(isDisplayed()))
    }
}
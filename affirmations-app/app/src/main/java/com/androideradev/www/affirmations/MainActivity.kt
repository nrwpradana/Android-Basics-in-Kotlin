package com.androideradev.www.affirmations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.androideradev.www.affirmations.adapter.AffirmationItemAdapter
import com.androideradev.www.affirmations.data.DataSource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val affirmationRecyclerView = findViewById<RecyclerView>(
            R.id.affirmation_recycler_view
        )

        affirmationRecyclerView.adapter = AffirmationItemAdapter(
            // Initialize data.
            DataSource.loadAffirmations(this)
        )
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        affirmationRecyclerView.setHasFixedSize(true)


    }
}
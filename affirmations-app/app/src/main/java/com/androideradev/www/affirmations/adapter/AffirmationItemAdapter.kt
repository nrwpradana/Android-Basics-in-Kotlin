package com.androideradev.www.affirmations.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androideradev.www.affirmations.R
import com.androideradev.www.affirmations.model.Affirmation

/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Affirmation] data object.
 */
class AffirmationItemAdapter(
    private val affirmations: List<Affirmation>
) : RecyclerView.Adapter<AffirmationItemAdapter.AffirmationItemViewHolder>() {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class AffirmationItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.affirmation_item_title_text_view)
        val imageView: ImageView = itemView.findViewById(R.id.affirmation_image_view)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AffirmationItemViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.affirmation_list_item, parent, false)
    )

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: AffirmationItemViewHolder, position: Int) {
        val affirmation = affirmations[position]
        holder.textView.text = affirmation.affirmationText
        holder.imageView.setImageResource(affirmation.imageResourceId)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = affirmations.size

}
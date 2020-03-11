package com.example.mynotes.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.ui.fragments.HomeFragmentDirections
import kotlinx.android.synthetic.main.row_layout.view.*


class NotesAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        context = parent.context
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.title_text_view.text = notes[position].title
        holder.itemView.note_text_view.text = notes[position].note

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)
        }
        var colorRes = 0
        when (position % 7) {
            0 -> colorRes = R.color.list_color1
            1 -> colorRes = R.color.list_color2
            2 -> colorRes = R.color.list_color3
            3 -> colorRes = R.color.list_color4
            4 -> colorRes = R.color.list_color5
            5 -> colorRes = R.color.list_color6
            6 -> colorRes = R.color.list_color7
        }
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context!!, colorRes))
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
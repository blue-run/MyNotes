package com.example.mynotes.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import com.example.mynotes.utils.toast
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {

    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            title.setText(note?.title)
            note_body.setText(note?.note)
        }

        floatingActionButton2.setOnClickListener { view ->

            val noteTitle = title.text.toString().trim()
            val noteBody = note_body.text.toString().trim()

            if (noteTitle.isEmpty()) {
                title.error = "title required"
                title.requestFocus()
                return@setOnClickListener
            }

            if (noteBody.isEmpty()) {
                note_body.error = "note required"
                note_body.requestFocus()
                return@setOnClickListener
            }

            launch {
                val mNote = Note(noteTitle, noteBody)

                context?.let {
                    if (note == null) {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Added")

                    } else {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note updated")
                    }

                    val action =
                        AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            if (note != null) {
                launch {
                    context?.let {
                        NoteDatabase(it).getNoteDao().deleteNote(note!!)
                        it.toast("Note Deleted")
                        val action =
                            AddNoteFragmentDirections.actionSaveNote()
                        Navigation.findNavController(view!!).navigate(action)
                    }
                }
            } else {
                context?.toast("Cannot Delete")
            }
        }

        return super.onOptionsItemSelected(item)
    }


}

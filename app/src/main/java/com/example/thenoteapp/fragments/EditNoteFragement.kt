package com.example.thenoteapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thenoteapp.MainActivity
import com.example.thenoteapp.R
import com.example.thenoteapp.databinding.FragmentEditNoteFragementBinding
import com.example.thenoteapp.model.Note
import com.example.thenoteapp.viewmodel.NoteViewModel


@SuppressLint("ResourceType")
class EditNoteFragement : Fragment(R.id.editNoteFragement),MenuProvider {

    private var editNoteBinding: FragmentEditNoteFragementBinding? = null
    private val binding get() = editNoteBinding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote : Note

    private val args: EditNoteFragementArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding = FragmentEditNoteFragementBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDec)

        binding.editNoteFab.setOnClickListener{
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if(noteTitle.isNotEmpty()){
                val note = Note(currentNote.id,noteTitle,noteDesc)

                notesViewModel.upDate(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                Toast.makeText(context,"Please enter note Title ",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply{
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?" )
            setPositiveButton("Delete"){ _,_ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context,"Note Deleted ",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("Cancel",null)

        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu ->{
                deleteNote()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}
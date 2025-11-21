package ch.heigvd.iict.daa.lab4_b.fragments.actions

import NotesViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.lab4_b.NotesApplication
import ch.heigvd.iict.daa.lab4_b.R
import ch.heigvd.iict.daa.lab4_b.viewmodels.NotesViewModelFactory

class ActionsFragment : Fragment() {
    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as NotesApplication).noteRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.action_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.generate_note_action).setOnClickListener {
            notesViewModel.generateANote()
        }

        view.findViewById<Button>(R.id.delete_notes_action).setOnClickListener {
            notesViewModel.deleteAllNote()
        }
    }
}
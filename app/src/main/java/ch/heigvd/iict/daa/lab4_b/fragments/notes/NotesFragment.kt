package ch.heigvd.iict.daa.lab4_b.fragments.notes

import NotesViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.lab4_b.NotesApplication
import ch.heigvd.iict.daa.lab4_b.R
import ch.heigvd.iict.daa.lab4_b.viewmodels.NotesViewModelFactory
import java.util.Calendar

class NotesFragment : Fragment() {

    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as NotesApplication).noteRepository)
    }
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.notes_view)
        val adapter = NotesViewAdapter(context=view.context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        notesViewModel.allNotes.observe(viewLifecycleOwner) { list ->
            adapter.items = list
        }
    }
}
package ch.heigvd.iict.daa.lab4_b.fragments.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.lab4_b.R
import ch.heigvd.iict.daa.lab4_b.models.Note
import ch.heigvd.iict.daa.lab4_b.models.NoteAndSchedule
import ch.heigvd.iict.daa.lab4_b.models.Schedule
import ch.heigvd.iict.daa.lab4_b.models.State
import ch.heigvd.iict.daa.lab4_b.models.Type
import java.util.Calendar

class NotesFragment : Fragment() {

    private var today = Calendar.getInstance()

    private var demoData = listOf(
        NoteAndSchedule(
            Note(0L, State.IN_PROGRESS, "t0", "c0", today, Type.SHOPPING), null),
        NoteAndSchedule(
            Note(1L, State.DONE, "t1", "c1", today, Type.WORK), null),
        NoteAndSchedule(
            Note(2L, State.IN_PROGRESS, "t2", "c2", today, Type.SHOPPING), Schedule(1,1, today)),
        NoteAndSchedule(
            Note(3L, State.IN_PROGRESS, "t3", "c3", today, Type.FAMILY), null)
        )

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

        adapter.items = demoData
    }
}
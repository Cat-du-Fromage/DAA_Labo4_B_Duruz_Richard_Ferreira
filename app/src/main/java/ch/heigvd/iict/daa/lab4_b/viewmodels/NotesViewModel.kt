import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.lab4_b.models.NoteAndSchedule
import ch.heigvd.iict.daa.lab4_b.repository.NoteRepository

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    // LiveData publiques (non modifiables)
    val allNotes: LiveData<List<NoteAndSchedule>>
        get() = repository.allNotes

    val countNotes: LiveData<Int>
        get() = repository.countNotes

    fun generateANote() {
        repository.generateANote()
    }

    fun deleteAllNote() {
        repository.deleteAllNotes()
    }
}

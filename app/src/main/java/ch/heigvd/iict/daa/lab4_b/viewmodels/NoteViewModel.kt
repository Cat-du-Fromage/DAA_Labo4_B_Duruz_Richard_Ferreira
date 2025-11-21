import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.lab4_b.models.NoteAndSchedule
import ch.heigvd.iict.daa.lab4_b.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    // LiveData publiques (non modifiables)
    val allNotes: LiveData<List<NoteAndSchedule>>
        get() = repository.notes

    val countNotes: LiveData<Int>
        get() = repository.noteCount

    fun generateANote() {
        repository.generateNote()
    }

    fun deleteAllNote() {
        repository.deleteNotes()
    }
}

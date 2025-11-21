import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.lab4_b.models.NoteAndSchedule
import ch.heigvd.iict.daa.lab4_b.repository.NoteRepository

enum class SortOrder {
    CREATION_DATE,
    DEADLINE
}

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _currentSortOrder = MutableLiveData(SortOrder.CREATION_DATE)

    val allNotes = MediatorLiveData<List<NoteAndSchedule>>().apply {
        // Si la base de données change (ajout/suppression de note)
        addSource(repository.allNotes) { notes ->
            value = sortNotes(notes, _currentSortOrder.value ?: SortOrder.CREATION_DATE)
        }
        // Si l'utilisateur change le tri
        addSource(_currentSortOrder) { order ->
            value = sortNotes(repository.allNotes.value ?: emptyList(), order)
        }
    }

    val countNotes: LiveData<Int>
        get() = repository.countNotes

    fun generateANote() {
        repository.generateANote()
    }

    fun deleteAllNote() {
        repository.deleteAllNotes()
    }

    private fun sortNotes(notes: List<NoteAndSchedule>, order: SortOrder): List<NoteAndSchedule> {
        return when (order) {
            SortOrder.CREATION_DATE -> notes.sortedBy { it.note.creationDate }
            SortOrder.DEADLINE -> notes.sortedBy{ it.schedule?.date }
        }
    }

    fun changeSortOrder(order: SortOrder) {
        _currentSortOrder.value = order
    }
}

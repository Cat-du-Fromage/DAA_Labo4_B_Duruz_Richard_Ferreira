package ch.heigvd.iict.daa.lab4_b

import android.app.Application
import ch.heigvd.iict.daa.lab4_b.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {

    /**
     * Initialize coroutine for database operations
     */
    private val scope = CoroutineScope(SupervisorJob())

    /**
     * Database singletons lazy initialization
     */
    val database by lazy { NotesDatabase.getInstance(this, scope).noteDao() }
    val noteRepository by lazy { NoteRepository(database, scope) }
}
package ch.heigvd.iict.daa.lab4_b.repository

import kotlinx.coroutines.*

class NoteRepository(
    private val noteDao: NoteDAO,
    private val applicationScope: CoroutineScope
) {

    /**
     * LiveData of all notes and schedules.
     */
    val notes = noteDao.findAll()

    /**
     * LiveData of number of notes.
     */
    val noteCount = noteDao.count()

    /**
     * Generate random note and schedule and insert them in database.
     */
    fun generateNote() {
        applicationScope.launch {
            noteDao.generateNote()
        }
    }

    /**
     * Delete all notes and schedules.
     */
    fun deleteNotes() {
        applicationScope.launch {
            noteDao.deleteNotes()
            noteDao.deleteSchedules()
        }
    }
}
package ch.heigvd.iict.daa.lab4_b.repository

import kotlinx.coroutines.*

/**
    NoteRepository.kt
     * Class that link Android Room and the desired function to the app
    Authors:
     * Duruz Florian
     * Ferreira Silva Sven
     * Richard Aurélien
 */
class NoteRepository(
    private val noteDao: NoteDAO,
    private val applicationScope: CoroutineScope
) {

    /**
     * LiveData of all notes and schedules.
     */
    val allNotes = noteDao.findAll()

    /**
     * LiveData of number of notes.
     */
    val countNotes = noteDao.count()

    /**
     * Generate random note and schedule and insert them in database.
     */
    fun generateANote() {
        applicationScope.launch {
            noteDao.generateNote()
        }
    }

    /**
     * Delete all notes and schedules.
     */
    fun deleteAllNotes() {
        applicationScope.launch {
            noteDao.deleteNotes()
            noteDao.deleteSchedules()
        }
    }
}
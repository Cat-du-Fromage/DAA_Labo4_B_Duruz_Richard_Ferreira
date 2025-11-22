package ch.heigvd.iict.daa.lab4_b.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.heigvd.iict.daa.lab4_b.models.*

/**
    NoteDAO.kt
     * Interface with all function we want to, to interact with the databases
    Authors:
     * Duruz Florian
     * Ferreira Silva Sven
     * Richard Aurélien
 */
@Dao
interface NoteDAO {
    /**
     * Get notes and schedules.
     * @return List of Observable notes and schedules
     */
    @Query("SELECT * FROM note")
    @Transaction
    fun findAll(): LiveData<List<NoteAndSchedule>>

    /**
     * Get number of notes.
     * @return Observable count of notes
     */
    @Query("SELECT COUNT(*) FROM note")
    fun count(): LiveData<Int>

    /**
     * Insert a note.
     * @param note Note to insert
     * @return Id of inserted note
     */
    @Insert
    fun insert(note: Note): Long

    /**
     * Insert schedule.
     * @param schedule Schedule to insert
     * @return Id of inserted schedule
     */
    @Insert
    fun insert(schedule: Schedule): Long

    /**
     * Delete all notes.
     */
    @Query("DELETE FROM note")
    fun deleteNotes()

    /**
     * Delete all schedules.
     */
    @Query("DELETE FROM schedule")
    fun deleteSchedules()

    /**
     * Generate random note and schedule, and insert it.
     * @return Id of inserted note
     */
    @Transaction
    fun generateNote(): Long {
        return insert(
            Note.generateRandomNote()
        ).also { noteId ->
            Note.generateRandomSchedule()?.let {
                it.ownerId = noteId
                insert(it)
            }
        }
    }
}
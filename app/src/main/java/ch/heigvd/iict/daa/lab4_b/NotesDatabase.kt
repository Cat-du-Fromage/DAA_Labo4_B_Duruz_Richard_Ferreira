
package ch.heigvd.iict.daa.lab4_b

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.daa.lab4_b.models.*
import ch.heigvd.iict.daa.lab4_b.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
    NotesDatabase.kt
    * Database singleton
    Authors:
    * Duruz Florian
    * Ferreira Silva Sven
    * Richard Aurélien
 */
@Database(entities = [Note::class, Schedule::class], version = 1, exportSchema = true)
@TypeConverters(CalendarConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    /**
     * Data access object for notes.
     */
    abstract fun noteDao(): NoteDAO

    companion object {
        private var instance: NotesDatabase? = null

        /**
         * Get Singleton instance of database.
         * @param context Context in which database is created
         * @param scope Scope in which database will run
         * @return Singleton instance of database
         */
        fun getInstance(context: Context, scope: CoroutineScope): NotesDatabase {
            return instance ?: synchronized(this) {
                return Room
                    .databaseBuilder(context.applicationContext, NotesDatabase::class.java, "notes_database.db")
                    .fallbackToDestructiveMigration(true)
                    .addCallback(databasePopulateCallback(scope))
                    .build()
                    .also { instance = it }
            }
        }

        /**
         * Populate database with some notes
         */
        private fun databasePopulateCallback(scope: CoroutineScope) = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                instance?.let { database ->
                    val dao = database.noteDao()
                    scope.launch { repeat(10) { dao.generateNote() } }
                }
            }
        }
    }
}
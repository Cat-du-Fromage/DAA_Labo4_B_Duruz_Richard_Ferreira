package ch.heigvd.iict.daa.lab4_b

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import ch.heigvd.iict.daa.lab4_b.viewmodels.NotesViewModel
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import ch.heigvd.iict.daa.lab4_b.fragments.actions.ActionsFragment
import ch.heigvd.iict.daa.lab4_b.fragments.notes.NotesFragment
import ch.heigvd.iict.daa.lab4_b.viewmodels.NotesViewModelFactory
import ch.heigvd.iict.daa.lab4_b.viewmodels.SortOrder
import kotlin.getValue

/**
    MainActivity.kt
     * Main Activity
    Authors:
     * Duruz Florian
     * Ferreira Silva Sven
     * Richard Aurélien
 */
class MainActivity : AppCompatActivity() {

    private lateinit var notesFragment: NotesFragment
    private var actionsFragment: ActionsFragment? = null

    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as NotesApplication).noteRepository, applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(findViewById(R.id.toolbar))

        this.notesFragment = findViewById<FragmentContainerView>(R.id.notes_fragment_container).getFragment()
        this.actionsFragment = findViewById<FragmentContainerView>(R.id.actions_fragment_container)?.getFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        if (this.actionsFragment == null) {
            menuInflater.inflate(R.menu.notes_action_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.delete_notes_action -> {
                notesViewModel.deleteAllNote()
                true
            }
            R.id.generate_note_action -> {
                notesViewModel.generateANote()
                true
            }
            R.id.sort_by_creation_date_action -> {
                notesViewModel.changeSortOrder(SortOrder.CREATION_DATE)
                true
            }
            R.id.sort_by_deadline_action -> {
                notesViewModel.changeSortOrder(SortOrder.DEADLINE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        newConfig.orientation = if (resources.getBoolean(R.bool.isTablet)) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        super.onConfigurationChanged(newConfig)
    }
}

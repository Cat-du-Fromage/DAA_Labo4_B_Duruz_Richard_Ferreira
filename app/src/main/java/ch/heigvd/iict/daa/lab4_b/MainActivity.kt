package ch.heigvd.iict.daa.lab4_b

import NotesViewModel
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
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    private lateinit var notesFragment: NotesFragment
    private var actionsFragment: ActionsFragment? = null

    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as NotesApplication).noteRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // depuis android 15 (sdk 35), le mode edge2edge doit être activé
        enableEdgeToEdge()

        // on spécifie le layout à afficher
        setContentView(R.layout.activity_main)

        // comme edge2edge est activé, l'application doit garder un espace suffisant pour la barre système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // la barre d'action doit être définie dans le layout, on la lie à l'activité
        setSupportActionBar(findViewById(R.id.toolbar))

        // TODO ...

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
                // CORRECT : On passe par le ViewModel
                notesViewModel.deleteAllNote()
                true
            }
            R.id.generate_note_action -> {
                // CORRECT : On demande au ViewModel de générer
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
}

package ch.heigvd.iict.daa.lab4_b.fragments.notes

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.lab4_b.R
import ch.heigvd.iict.daa.lab4_b.models.NoteAndSchedule
import ch.heigvd.iict.daa.lab4_b.models.State
import ch.heigvd.iict.daa.lab4_b.models.Type
import java.util.Calendar
import kotlin.math.truncate

class NotesViewAdapter(_items : List<NoteAndSchedule> = listOf(), val context: Context) : RecyclerView.Adapter<NotesViewAdapter.ViewHolder>() {
    var items = listOf<NoteAndSchedule>()
        set(value) {
            val diffCallback = NotesDiffCallback(items, value)
            val diffItems = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffItems.dispatchUpdatesTo(this) // à la place de notifyDatasetChanged()
        }
    init { items = _items }
    override fun getItemCount() = items.size
    /*
    override fun getItemViewType(position: Int): Int {
        if(items[position] is Mammal) return MAMMAL
        else return AVES
    }
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_view_item, parent, false), context)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        private val noteImage = view.findViewById<ImageView>(R.id.note_image)
        private val noteTitle = view.findViewById<TextView>(R.id.note_title_text)
        private val noteDesc = view.findViewById<TextView>(R.id.note_desc_text)
        private val noteScheduleImage = view.findViewById<ImageView>(R.id.schedule_clock)
        private val noteScheduleText = view.findViewById<TextView>(R.id.schedule_text)
        fun bind(noteAndSchedule: NoteAndSchedule) {
            val note = noteAndSchedule.note
            // Note icon
            when(note.type) {
                Type.NONE -> noteImage.setImageResource(R.drawable.note)
                Type.SHOPPING -> noteImage.setImageResource(R.drawable.shopping)
                Type.FAMILY -> noteImage.setImageResource(R.drawable.family)
                Type.TODO -> noteImage.setImageResource(R.drawable.todo)
                Type.WORK -> noteImage.setImageResource(R.drawable.work)
            }

            // If is done, add a green overlay
            if (note.state == State.DONE) {
                noteImage.setColorFilter(Color.argb(255, 0, 200, 0))
            }

            // Note texts
            noteTitle.text = note.title
            noteDesc.text = note.text

            // Note schedule, if present
            if (noteAndSchedule.schedule != null && note.state == State.IN_PROGRESS) {
                val isLate = Calendar.getInstance() > noteAndSchedule.schedule.date

                noteScheduleImage.setImageResource(R.drawable.clock)
                if (isLate) {
                    noteScheduleImage.setColorFilter(Color.argb(255, 200, 0, 0))
                    noteScheduleText.text = context.resources.getString(R.string.schedule_past_date)
                } else {
                    noteScheduleText.text = mapDuration(noteAndSchedule.schedule.date)
                }
            }
        }

        private fun mapDuration(date: Calendar) : String {
            val today = Calendar.getInstance()

            val diff = date.timeInMillis - today.timeInMillis
            // Using arithmetic to simplify various cases (e.g. a note in a year and a month)
            // For the looks, we'll only display the highest time granularity.
            // Example: a note to be done in 3 months and 23 days -> display in 3 months
            // Or: a note to be done in a year and a month -> display in a year
            // a note to be done in 24 days -> display in 24 days...
            val days = diff / 86400000
            val months = truncate(days / 30.5).toInt()
            val years = months / 12

            return if (years > 0) {
                context.resources.getQuantityString(R.plurals.schedule_year_unit, years, years)
            } else if (months > 0) {
                context.resources.getQuantityString(R.plurals.schedule_month_unit, months, months)
            } else if (today.get(Calendar.DAY_OF_YEAR) != date.get(Calendar.DAY_OF_YEAR)) {
                val daysDiff = (date.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR)) % 365
                context.resources.getQuantityString(R.plurals.schedule_day_unit, daysDiff, daysDiff) // Safe to cast to int as days would necessarily be less than 30.5 on average
            } else {
                context.resources.getString(R.string.schedule_same_day)
            }
        }
    }

    fun deleteAll() : Boolean {
        items = listOf()
        return true
    }

    fun addNote(note: NoteAndSchedule) : Boolean {
        items += note
        return true
    }

    fun reorderByField(field: String) : Boolean {
        if (field == "creationDate") {
            items = items.sortedBy { it.note.creationDate }
        } else if (field == "deadline") {
            items = items.sortedBy { it.schedule?.date }
        }
        return true
    }
}
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

/**
    NotesViewAdapter.kt
     * Adapter use with RecyclerView and ViewHolder
    Authors:
     * Duruz Florian
     * Ferreira Silva Sven
     * Richard Aurélien
 */
class NotesViewAdapter(private val context: Context) : RecyclerView.Adapter<NotesViewAdapter.ViewHolder>() {

    private val TYPE_NOTE = 0
    private val TYPE_NOTE_WITH_SCHEDULE = 1

    var items = listOf<NoteAndSchedule>()
        set(value) {
            val diffCallback = NotesDiffCallback(field, value)
            val diffItems = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffItems.dispatchUpdatesTo(this)
        }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position].schedule != null) TYPE_NOTE_WITH_SCHEDULE else TYPE_NOTE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if (viewType == TYPE_NOTE_WITH_SCHEDULE) {
            R.layout.notes_view_item_schedule // Idéalement : R.layout.notes_view_item_schedule
        } else {
            R.layout.notes_view_item
        }
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false), context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        private val noteImage = view.findViewById<ImageView>(R.id.note_image)
        private val noteTitle = view.findViewById<TextView>(R.id.note_title_text)
        private val noteDesc = view.findViewById<TextView>(R.id.note_desc_text)

        private val noteScheduleImage = view.findViewById<ImageView?>(R.id.schedule_clock)
        private val noteScheduleText = view.findViewById<TextView?>(R.id.schedule_text)

        fun bind(noteAndSchedule: NoteAndSchedule) {
            val note = noteAndSchedule.note
            when(note.type) {
                Type.NONE -> noteImage.setImageResource(R.drawable.note)
                Type.SHOPPING -> noteImage.setImageResource(R.drawable.shopping)
                Type.FAMILY -> noteImage.setImageResource(R.drawable.family)
                Type.TODO -> noteImage.setImageResource(R.drawable.todo)
                Type.WORK -> noteImage.setImageResource(R.drawable.work)
            }

            if (note.state == State.DONE) {
                noteImage.setColorFilter(Color.argb(255, 0, 200, 0))
            } else {
                noteImage.clearColorFilter()
            }

            noteTitle.text = note.title
            noteDesc.text = note.text

            if (noteAndSchedule.schedule != null && note.state == State.IN_PROGRESS) {
                noteScheduleImage?.let { img ->
                    val isLate = Calendar.getInstance() > noteAndSchedule.schedule.date
                    img.setImageResource(R.drawable.clock)
                    if (isLate) {
                        img.setColorFilter(Color.argb(255, 200, 0, 0))
                        noteScheduleText?.text = context.resources.getString(R.string.schedule_past_date)
                    } else {
                        img.clearColorFilter()
                        noteScheduleText?.text = mapDuration(noteAndSchedule.schedule.date)
                    }
                    img.visibility = View.VISIBLE
                    noteScheduleText?.visibility = View.VISIBLE
                }
            } else {
                noteScheduleImage?.visibility = View.GONE
                noteScheduleText?.visibility = View.GONE
            }
        }

        private fun mapDuration(date: Calendar) : String {
            val today = Calendar.getInstance()
            val diff = date.timeInMillis - today.timeInMillis
            val days = diff / 86400000
            val months = truncate(days / 30.5).toInt()
            val years = months / 12

            return if (years > 0) {
                context.resources.getQuantityString(R.plurals.schedule_year_unit, years, years)
            } else if (months > 0) {
                context.resources.getQuantityString(R.plurals.schedule_month_unit, months, months)
            } else if (today.get(Calendar.DAY_OF_YEAR) != date.get(Calendar.DAY_OF_YEAR)) {
                val daysDiff = (date.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR)) % 365
                context.resources.getQuantityString(R.plurals.schedule_day_unit, daysDiff, daysDiff)
            } else {
                context.resources.getString(R.string.schedule_same_day)
            }
        }
    }
}
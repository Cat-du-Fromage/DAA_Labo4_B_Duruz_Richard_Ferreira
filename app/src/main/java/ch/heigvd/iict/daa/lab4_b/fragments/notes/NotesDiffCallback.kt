package ch.heigvd.iict.daa.lab4_b.fragments.notes

import androidx.recyclerview.widget.DiffUtil
import ch.heigvd.iict.daa.lab4_b.models.NoteAndSchedule

class NotesDiffCallback(private val oldList: List<NoteAndSchedule>, private val newList: List<NoteAndSchedule>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].note.noteId == newList[newItemPosition].note.noteId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old::class == new::class && old.note.text == new.note.text && new.note.title == old.note.title
    }
}
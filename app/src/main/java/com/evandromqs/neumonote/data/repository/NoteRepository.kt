package com.evandromqs.neumonote.data.repository

import com.evandromqs.neumonote.data.local.NoteDao
import com.evandromqs.neumonote.data.local.NoteEntity

class NoteRepository(private val noteDao: NoteDao) {
    val notes = noteDao.observeAll()

    suspend fun findById(id: Long): NoteEntity? = noteDao.findById(id)

    suspend fun save(note: NoteEntity): Long = noteDao.insert(
        note.copy(updatedAt = System.currentTimeMillis())
    )

    suspend fun delete(note: NoteEntity) = noteDao.delete(note)
}
package com.evandromqs.neumonote.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NeumoNoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NeumoNoteDatabase? = null

        fun getInstance(context: Context): NeumoNoteDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NeumoNoteDatabase::class.java,
                    "neumonote.db"
                ).build().also { instance = it }
            }
    }
}
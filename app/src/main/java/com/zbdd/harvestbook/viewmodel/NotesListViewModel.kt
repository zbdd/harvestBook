package com.zbdd.harvestbook.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.zbdd.harvestbook.model.room.AppDatabase
import javax.inject.Inject

/**
 * NotesListViewModel handles state of properties shown in NotesListView
 *
 * @author Zac Durber
 */
class NotesListViewModel @Inject constructor(): ViewModel() {

    init {
        val db = AppDatabase.getInstance()
    }
}
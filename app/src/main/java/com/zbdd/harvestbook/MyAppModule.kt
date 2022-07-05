package com.zbdd.harvestbook

import com.zbdd.harvestbook.view.NotesListView
import com.zbdd.harvestbook.viewmodel.NotesListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * Class MyAppModule required for instantiating our dependency injections for Hilt
 *
 * @author Zac Durber
 */
@Module
@InstallIn(SingletonComponent::class)
class MyAppModule {
    @Provides
    fun providesNotesListView(notesListViewModel: NotesListViewModel): NotesListView = NotesListView()
}
package com.zbdd.harvestbook.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zbdd.harvestbook.model.INote
import com.zbdd.harvestbook.view.theme.HarvestBookTheme
import com.zbdd.harvestbook.viewmodel.NotesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * NotesListView is the main UI for displaying Notes as a List. The UI is constructed using
 * the latest Compose libraries and the functions are recalled to update the view upon certain actions.
 * State is controlled by the injected viewModel NotesListViewModel
 *
 * @author Zac Durber
 */
@AndroidEntryPoint
class NotesListView @Inject constructor() : ComponentActivity() {
    @Inject
    lateinit var viewModel: NotesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            main()
        }
    }

    /**
     * Displays the top row and enables to ability to click each filter title
     * to trigger a sorting of the notes list
     */
    @Composable
    fun topRow(
        filter1Title: String,
        filter2Title: String,
        onFilterClick: (String) -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(all = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = filter1Title,
                Modifier.clickable { onFilterClick(filter1Title) },
                fontWeight = FontWeight.Bold
            )
            Text(
                text = filter2Title,
                Modifier.clickable { onFilterClick(filter2Title) },
                fontWeight = FontWeight.Bold
            )
        }
    }

    /**
     * Main composable function that enables the applies the theme, surface and general
     * structure of our list view.
     */
    @Composable
    fun main() {
        HarvestBookTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (viewModel.displayTopRow)
                        topRow(
                            filter1Title = "Title",
                            filter2Title = "Last Updated",
                            onFilterClick = { viewModel.sortListBy(it) }
                        )
                    if (viewModel.displayDetail.value.id != -1) displayDetail(viewModel.displayDetail)
                    else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                                .heightIn(
                                    0.dp,
                                    LocalConfiguration.current.screenHeightDp.times(0.8).dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            items(viewModel.noteList) { mood ->
                                if (mood.id != -1) displayItem(mood)
                            }
                        }
                        displayAddButton("New")
                    }
                }
            }
        }
    }

    /**
     * Compose function to render a simple add button
     *
     * @param title - name of the button
     */
    @Composable
    fun displayAddButton(
        title: String
    ) {
        Button({ viewModel.addNewNote() }) {
            Text(text = title)
        }
    }

    /**
     * Compose function to display a Note in detail - so that a user may edit each individual
     * field.
     *
     * @param note - an instance of an INote we wish to view/edit
     */
    @Composable
    fun displayDetail(note: MutableState<INote>) {

        var title by rememberSaveable { mutableStateOf(note.value.title ?: "") }
        var content by rememberSaveable { mutableStateOf(note.value.content ?: "") }

        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(15.dp)),
            verticalArrangement = Arrangement.Top,
        ) {

            TextField(value = title,
                onValueChange = { title = it },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.DarkGray,
                    backgroundColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent),
                textStyle = MaterialTheme.typography.h2,
            placeholder = { Text(text = "Click to change...") })

            Text(
                text = viewModel.dateTimeEnhancer(note.value.updated.toString()),
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 15.dp)
            )

            TextField(value = content,
                onValueChange = { content = it },colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent),
                placeholder = { Text(text = "Click to change...") })
        }
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { note.value.title = title; note.value.content = content; viewModel.saveNote(note.value); viewModel.returnToList(); }) {
                Text(text = "Save")
            }
        }
    }

    /**
     * Compose function to render an individual Note row to the screen.
     * Allows us to click on it to view in detail, or to swipe to the right to dismiss
     *
     * @param note - an instance of an INote
     */
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun displayItem(
        note: INote
    ) {
        val swipeState = rememberDismissState(
            confirmStateChange = {
                if (it == DismissValue.DismissedToEnd) {
                    viewModel.removeNote(note)
                }
                true
            }
        )

        SwipeToDismiss(modifier = Modifier
            .clickable(onClick = { viewModel.setupDetailedView(note) }),
            directions = setOf(DismissDirection.StartToEnd),
            state = swipeState,
            dismissThresholds = { FractionalThreshold(0.6f) },
            background = {
                val colour by animateColorAsState(
                    targetValue = when (swipeState.targetValue) {
                        else -> Color.Red
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxWidth()
                        .background(colour, shape = RoundedCornerShape(15.dp))
                        .padding(all = 15.dp)
                ) {
                    Text(text = "")
                }
            },
            dismissContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxWidth()
                        .background(Color.LightGray, shape = RoundedCornerShape(15.dp))
                        .padding(all = 15.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = note.title.toString())
                    Text(text = viewModel.dateTimeEnhancer(note.updated.toString()))
                }
            }
        )
    }

    /**
     * Only used in the editor to see UI changes on the fly
     */
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HarvestBookTheme {
            main()
        }
    }
}
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    @Composable
    fun main() {
        val note = viewModel.displayDetail
        val noteList = viewModel.noteList

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
                            onFilterClick = { viewModel.sortListBy(it); setContent { main() } }
                        )
                    if (note != null) displayDetail(note)
                    else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                                .heightIn(0.dp, LocalConfiguration.current.screenHeightDp.times(0.8).dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            items(noteList) { mood ->
                                displayItem(mood)
                            }
                        }
                        displayAddButton("Add")
                    }
                }
            }
        }
    }

    @Composable
    fun displayAddButton(
        title: String
    ) {
        Button({ viewModel.addNewNote(); setContent { main() } }) {
            Text(text = title)
        }
    }

    @Composable
    fun displayDetail(note: INote) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(15.dp)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = note.title.toString(), fontWeight = FontWeight.Bold)
            Text(
                text = viewModel.dateTimeEnhancer(note.updated.toString()),
                fontStyle = FontStyle.Italic
            )
            Text(text = note.content.toString())
            Button(onClick = { viewModel.returnToList(); setContent { viewModel.saveNote(note); main() } }) {
                Text(text = "Close")
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun displayItem(
        note: INote
    ) {
        val swipeState = rememberDismissState(
            confirmStateChange = {
                if (it == DismissValue.DismissedToEnd) {
                    viewModel.removeNote(note); setContent { main() }
                }
                true
            }
        )

        SwipeToDismiss(modifier = Modifier
            .clickable(onClick = { setContent { viewModel.setupDetailedView(note); main() } }),
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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HarvestBookTheme {
            main()
        }
    }
}
package com.example.notetakingapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

data class Note(val title: String, val description: String)

@Composable
fun NotesTakingApp() {
    // State management
    var notes by remember { mutableStateOf(listOf(
        Note("Sample Note 1", "This is a sample description"),  // Sample placeholder
        Note("Sample Note 2", "Another sample description")     // Sample description for the second note
    )) }
    var showAddDialog by remember { mutableStateOf(false) }
    var newNoteTitle by remember { mutableStateOf("") }
    var newNoteDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Notes Taking App",  // App Title
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Note Button
        Button(
            onClick = { showAddDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Note")   // Add a new note
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notes List
        if (notes.isEmpty()) {
            Text(
                text = "No notes available", // Placeholder text
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notes) { note ->
                    NoteCard(note) // Display each note
                }
            }
        }
    }

    // Add Note Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add New Note") },   // Title of the dialog
            text = {
                Column {
                    OutlinedTextField(
                        value = newNoteTitle,
                        onValueChange = { newNoteTitle = it },
                        label = { Text("Title") }, // Label for the title field
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newNoteDescription,
                        onValueChange = { newNoteDescription = it },
                        label = { Text("Description") }, // Label for the description field
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },

            // Confirm Button
            confirmButton = {
                Button(
                    onClick = {
                        if (newNoteTitle.isNotBlank() && newNoteDescription.isNotBlank()) {
                            notes = notes + Note(newNoteTitle, newNoteDescription)
                            newNoteTitle = ""
                            newNoteDescription = ""
                            showAddDialog = false
                        }
                    },
                    enabled = newNoteTitle.isNotBlank() && newNoteDescription.isNotBlank()
                ) {
                    Text("Add")
                }
            },

            // Cancel Button
            dismissButton = {
                TextButton(onClick = {
                    showAddDialog = false
                    newNoteTitle = ""
                    newNoteDescription = ""
                }) {
                    Text("Cancel")
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false)
        )
    }
}


// Note Card
@Composable
fun NoteCard(note: Note) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
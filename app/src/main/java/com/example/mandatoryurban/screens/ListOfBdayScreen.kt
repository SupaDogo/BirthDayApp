package com.example.mandatoryurban.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mandatoryurban.Model.PersonViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mandatoryurban.Model.Person
import com.example.mandatoryurban.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale


val tealColor = Color(0xFF00CED1)
val mainColor = Color.White
val secondaryColor = Color(0xFFBDBDBD)
var user = ""


@Composable
fun ListOfBday(onNavigateBack: () -> Unit, userName: String) {
    user = userName
    val viewModel: PersonViewModel = viewModel()

    Scaffold(
        topBar = {
            BirthdayListTopAppBar(
                navigateBack = onNavigateBack,
                onSortClick = { sortOption, ascending -> if (sortOption == "age")
                {viewModel.sortFriendsByAge(ascending)}
                else if (sortOption == "date") {viewModel.sortFriendsByDate(ascending)}
                else { viewModel.sortFriendsByName(ascending)}},
                onFilterClick = { filterText -> viewModel.filterByName(filterText)}
            )
        }
    ) { innerPadding ->
        listBday(
            modifier = Modifier.padding(innerPadding),
            userName = userName,
            viewModel
        )
    }
}


@Composable
fun listBday(modifier: Modifier,userName: String,viewModel: PersonViewModel){
    val persons = viewModel.persons.value



    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp)
        .clip(RoundedCornerShape(8.dp))
        .padding(8.dp)) {
        items(persons) {person ->
            if(userName == person.userId) {
                personTile(person, Delete = { viewModel.deletePerson(person.id) })
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
        item {
            addTile()
        }
    }




}

@Composable
fun personTile(person: Person, Delete: () -> Unit){
    var showDialog by rememberSaveable { mutableStateOf(false) }

        Row(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(Color(255, 255, 255, 99))
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))
            .padding(2.dp)
            .clickable {
                showDialog = true
            }
            ,verticalAlignment = Alignment.CenterVertically
            ,horizontalArrangement = Arrangement.SpaceBetween) {
            if(showDialog){
                Dialog(onDismissRequest = { showDialog = false }) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        PersonFields(close= {showDialog = false}, person = person)
                    }
                }
            }

            if(person.birthDayOfMonth == LocalDateTime.now().dayOfMonth && person.birthMonth == LocalDateTime.now().monthValue){
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Cake",
                    tint = tealColor
                )
            }
            Text(
                text = person.name.toString(),
                color = mainColor,
                modifier = Modifier.fillMaxWidth(fraction = 0.25f),
                fontWeight = FontWeight.SemiBold,
                fontFamily = fontFamily,
                fontSize = 16.sp
                )

            Spacer(Modifier.weight(0.5f))
            Text(
                text = "Age: " + person.age.toString(),
                color = Color.LightGray,
                fontFamily = fontFamily,
                fontSize  = 13.sp

            )
            Spacer(Modifier.weight(0.5f))
            Text(
                text = person.birthDayOfMonth.toString() + ".",
                color = tealColor,
                fontFamily = fontFamily,
                fontSize  = 13.sp
            )
            Text(
                text = person.birthMonth.toString() + "/",
                color = tealColor,
                fontFamily = fontFamily,
                fontSize  = 13.sp
            )
            Text(
                text = person.birthYear.toString(),
                color = tealColor,
                fontFamily = fontFamily,
                fontSize  = 13.sp
            )

            Spacer(modifier = Modifier.weight(0.25f))
            TextButton(onClick = {Delete()}) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = secondaryColor

                )
            }

        }
}

@Composable
fun addTile(){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        var showDialog by rememberSaveable { mutableStateOf(false) }
        TextButton(onClick = { showDialog = true
        }) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add",
                tint = tealColor

            )
        }
        if(showDialog){
            Dialog(onDismissRequest = { showDialog = false }) {
                Column(modifier = Modifier.padding(16.dp)) {
                    PersonFields(close= {showDialog = false})
                }
            }
        }

    }
}



@Composable
fun PersonFields(close: () -> Unit, person:Person? = null) {
    var friendName by rememberSaveable { mutableStateOf(if(person != null) "${person.name}" else ("")) }
    var userId = user
    var birthDay by rememberSaveable { mutableStateOf("") }
    var birthMonth by rememberSaveable { mutableStateOf("") }
    var birthYear by rememberSaveable { mutableStateOf("") }
    var remarks by rememberSaveable { mutableStateOf(if(person != null) "${person.remarks}" else ("")) }
    val viewModel: PersonViewModel = viewModel()
    var showDatePicker by remember { mutableStateOf(false) }
    var birth by rememberSaveable { mutableStateOf(if(person != null) "${person.birthDayOfMonth}" + "/" + "${person.birthMonth}" + "/" + "${person.birthYear}" else ("")) }




    Column(Modifier.background(Color(0, 0, 0, 129))) {
        OutlinedTextField(
            value = friendName,
            onValueChange = { friendName = it },
            label = {
                Text(
                    stringResource(R.string.friend_s_name),
                    fontFamily = fontFamily,
                    color = mainColor
                )
            },
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = birth,
            onValueChange = { },
            label = {
                Text(
                    "BirthDay",
                    fontFamily = fontFamily,
                    color = mainColor
                )
            },
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        Icons.Filled.DateRange, contentDescription = "Select date", tint = tealColor
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (showDatePicker) {
            Dialog(onDismissRequest = { showDatePicker = false }) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DatePickerModalInput(
                        onDismiss = { showDatePicker = false },
                        onDateSelected = { long -> birth = convertMillisToDate(long!!) })
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = remarks,
            onValueChange = { remarks = it },
            label = {
                Text(
                    "Remarks",
                    fontFamily = fontFamily,
                    color = mainColor
                )
            },
        )
        Spacer(modifier = Modifier.height(8.dp))


        if (person != null) {
            TextButton(onClick = {
                birthDay = birth.split("/")[0]
                birthMonth = birth.split("/")[1]
                birthYear = birth.split("/")[2]
                if (friendName.isNotBlank()
                    && birthDay.isNotBlank()
                    && birthMonth.isNotBlank()
                    && birthYear.isNotBlank()
                ) {
                    val personNew = Person(
                        person.id,
                        userId,
                        friendName,
                        birthYear.toInt(),
                        birthMonth.toInt(),
                        birthDay.toInt(),
                        remarks,
                        null,
                        0
                    )
                    viewModel.updatePerson(person.id, personNew)
                    close()
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit",
                    tint = tealColor

                )
            }
        }else{
        TextButton(onClick = {
            birthDay = birth.split("/")[0]
            birthMonth = birth.split("/")[1]
            birthYear = birth.split("/")[2]
            if (friendName.isNotBlank()
                && birthDay.isNotBlank()
                && birthMonth.isNotBlank()
                && birthYear.isNotBlank()
            ) {
                val person = Person(
                    viewModel.getLastId(),
                    userId,
                    friendName,
                    birthYear.toInt(),
                    birthMonth.toInt(),
                    birthDay.toInt(),
                    remarks,
                    null,
                    0
                )
                viewModel.addPerson(person)
                close()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add",
                tint = tealColor

            )
        }
    }

        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayListTopAppBar(
    navigateBack: () -> Unit,
    onSortClick: (sortOption: String, ascending: Boolean) -> Unit = { _, _ -> },
    onFilterClick: (filterOption: String) -> Unit = { _ -> }) {
    var showSortMenu by remember { mutableStateOf(false) }
    var showFilterMenu by remember { mutableStateOf(false) }
    var ascending by remember { mutableStateOf(true) }
    var filterText by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            Text(
                text = "Birthday Reminder",
                color = mainColor,
                fontFamily = fontFamily
            )
        },
        actions = {
            // Filter Button with dropdown menu

            Box {
                IconButton(onClick = { showFilterMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Filter Options",
                        tint = mainColor
                    )
                }

                DropdownMenu(
                    expanded = showFilterMenu,
                    onDismissRequest = { showFilterMenu = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            OutlinedTextField(
                                value = filterText,
                                onValueChange = { filterText = it },
                                label = { Text("Filter by name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                trailingIcon = {
                                    if (filterText.isNotEmpty()) {
                                        IconButton(onClick = {
                                            filterText = ""
                                            onFilterClick("")
                                        }) {
                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = "Clear",
                                                tint = mainColor
                                            )
                                        }
                                    }
                                }
                            )
                        },
                        onClick = { }
                    )
                    DropdownMenuItem(
                        text = { Text("Apply Filter") },
                        onClick = {
                            onFilterClick(filterText)
                            showFilterMenu = false
                        }
                    )
                }
            }

            Box {


                IconButton(onClick = { showSortMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Filter Options",
                        tint = mainColor
                    )
                }

                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = { showSortMenu = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Sort by Age")
                                if (ascending) Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Ascending")
                                else Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Descending")
                            }
                        },
                        onClick = {
                            ascending = !ascending
                            onSortClick("age", ascending)
                            showSortMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Sort by Date")
                                if (ascending) Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Ascending")
                                else Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Descending")
                            }
                        },
                        onClick = {
                            ascending = !ascending
                            onSortClick("date", ascending)
                            showSortMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Sort by Name")
                                if (ascending) Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Ascending")
                                else Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Descending")
                            }
                        },
                        onClick = {
                            ascending = !ascending
                            onSortClick("name", ascending)
                            showSortMenu = false
                        }
                    )
                }
            }


            // Sign Out Button
            IconButton(onClick = {
                authViewModel.signOut()
                navigateBack()
            }) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Sign Out",
                    tint = mainColor
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = tealColor,
            titleContentColor = mainColor,
            actionIconContentColor = mainColor,
            navigationIconContentColor = mainColor,
            scrolledContainerColor = tealColor
        )
    )
}


@Preview(showBackground = true)
@Composable
fun addPersonFieldsPreview(){
    PersonFields(close = {})
}

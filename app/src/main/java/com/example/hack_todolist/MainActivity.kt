package com.example.hack_todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.hack_todolist.Models.Event
import com.example.hack_todolist.Retrofit.EventsApi
import com.example.hack_todolist.Retrofit.RetrofitHelper
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.*
import kotlinx.coroutines.android.awaitFrame
import java.time.Instant


class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "taskdb"
        ).allowMainThreadQueries().build()

        val eventDao = db.taskDao()
        val eventApi = RetrofitHelper.getInstance().create(EventsApi::class.java)

        GlobalScope.launch(Dispatchers.IO + CoroutineExceptionHandler{ _, exception ->
            println("CoroutineExceptionHandler got $exception") }) {
            val result = eventApi.getEvents(1)
            val events = mutableListOf<com.example.hack_todolist.Entities.Event>()
            result.body()!!.events.forEach { it ->
                events.add(
                    com.example.hack_todolist.Entities.Event(
                        it.title,
                        it.description,
                        it.createdAt,
                        it.color,
                        it.startTime,
                        it.endTime,
                        it.tags.toString()
                    )
                )
            }
            db.taskDao().insertAll(*events.toList().toTypedArray())
            Log.d("ayuqwe", events.toString())
        }
        setContent {
            MainScreenView(db)
        }


    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenView(taskDatabase:TaskDatabase){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavigationGraph(navController = navController, taskDatabase = taskDatabase)
    }
}





sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_baseline_calendar_month_24,"home")
    object MyNetwork: BottomNavItem("Profile",R.drawable.ic_baseline_person_24,"my_network")
    object AddPost: BottomNavItem("Add",R.drawable.ic_baseline_add_24,"add_post")
}

@Composable
fun NavigationGraph(navController: NavHostController,taskDatabase: TaskDatabase) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen(taskDatabase)
        }
        composable(BottomNavItem.MyNetwork.screen_route) {
            NetworkScreen()
        }
        composable(BottomNavItem.AddPost.screen_route) {
            AddPostScreen(taskDatabase)
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Preview()
@Composable
fun ShowTag(isSelected: Boolean = false,text: String = "",){
    var selected by remember {
        mutableStateOf(isSelected)
    }
    FilterChip(
        selected = selected,
        onClick = {selected=!selected},
        modifier = Modifier.padding(3.dp),
        border = ChipDefaults.outlinedBorder,
        colors = ChipDefaults.outlinedFilterChipColors(selectedBackgroundColor = Color.Magenta, disabledBackgroundColor = Color.Magenta, disabledContentColor = Color.Black,)
    ) {
        Text(
            text = text,
        )
    }
}

fun getTags(): List<Tag> {
    return List<Tag>(5){Tag(1,"11")}
}

@Preview(showBackground = true)
@Composable
fun ChipGroup(
    tags: List<Tag> = getTags(),
    selectedCar: Tag? = null,
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow {
            items(tags) {
                ShowTag(text=it.name,isSelected = selectedCar == it,)

            }
        }
    }
}





@Composable
fun HomeScreen(database: TaskDatabase) {

    Row(Modifier.padding(bottom = 60.dp)) {
        val events = mutableListOf<Event>()
        database.taskDao().getAll().forEach {
            events.add(Event(it.title,
            it.description,it.createdAt,it.color,it.startTime,it.endTime, listOf(Tag(1,it.tags)))) }
        Schedule(events.toList())
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun NetworkScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
    }
    Row(modifier = Modifier
        .padding(start = 50.dp, end = 50.dp)
        .fillMaxWidth()
    ){
        var title by remember { mutableStateOf("") }
        OutlinedTextField(
            value = title,
            onValueChange = {title = it},
            maxLines = 1,
            label = {Text(text = "Authorization")}
        )
    }
}

@Composable
fun AddPostScreen(database: TaskDatabase) {
    var colorHex by remember {
        mutableStateOf("12")
    }
    var title by remember {
        mutableStateOf("12")
    }
    var description by remember {
        mutableStateOf("12")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Row(modifier = Modifier
            .padding(start = 50.dp, end = 50.dp)
            .fillMaxWidth()){
            OutlinedTextField(
                value = title,
                onValueChange = {title = it},
                maxLines = 1,
                label = {Text(text = "title")}
            )
        }
        Row(modifier = Modifier
            .padding(start = 50.dp, end = 50.dp)
            .fillMaxWidth()){
            OutlinedTextField(
                value = description,
                onValueChange = {description = it},
                label = {Text(text = "Description")},
                maxLines = 15,


                )
        }
        var colorPickerIsEnabled by remember {
            mutableStateOf(false)
        }
        ExtendedFloatingActionButton(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_colorize_24),contentDescription = "") },
            onClick = { colorPickerIsEnabled=!colorPickerIsEnabled},
            modifier = Modifier.padding(start= 50.dp),
            text = { Text(text=if (colorPickerIsEnabled) "Close" else "Select color") },
        )
        Row(){

            val controller = rememberColorPickerController()
            if (colorPickerIsEnabled)
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(10.dp)
                    ,
                    controller = controller,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        val color: Color = colorEnvelope.color // ARGB color value.
                        colorHex = colorEnvelope.hexCode // Color hex code, which represents color value.
                        val fromUser: Boolean = colorEnvelope.fromUser // Represents this event is triggered by user or not.
                    }
                )
        }
        val eventApi = RetrofitHelper.getInstance().create(EventsApi::class.java)
        Button(onClick = { database.taskDao().insertAll(com.example.hack_todolist.Entities.Event(title,description,Instant.now().epochSecond,
            "#$colorHex",Instant.now().epochSecond+3600,Instant.now().epochSecond+7200,
            listOf<Tag>(Tag(1,"12")).toString()
        ))
        }, modifier = Modifier.padding(50.dp),) {

            Text(text = "Add event")

        }


    }
}




@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyNetwork,
        BottomNavItem.AddPost,
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
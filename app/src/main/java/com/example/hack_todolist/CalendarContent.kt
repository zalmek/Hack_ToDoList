package com.example.hack_todolist

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorLong
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hack_todolist.Models.Event
import com.example.hack_todolist.ui.theme.WeekScheduleTheme
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt


val EventTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")

@Composable
fun BasicEvent(
    event: Event,
    modifier: Modifier = Modifier,
) {
    Column(

        modifier = modifier
            .clickable {}
            .fillMaxSize()
            .padding(end = 2.dp, bottom = 2.dp)
            .background(Color(android.graphics.Color.parseColor(event.color)), shape = RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {
        Text(
            text = "${
                LocalDateTime.ofInstant(Instant.ofEpochSecond(event.startTime), ZoneId.systemDefault())
                    .format(EventTimeFormatter)
            } - ${
                LocalDateTime.ofInstant(Instant.ofEpochSecond(event.endTime), ZoneId.systemDefault())
                    .format(EventTimeFormatter)
            }",
            style = MaterialTheme.typography.caption,
        )

        Text(
            text = event.title,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = event.description,
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

//private val sampleEvents =
//    listOf(
//    Event(
//        tags = listOf(),
//        title = "Google I/O Keynote",
//        color = 0xFFAFBBF2.toString(),
//        startTime = LocalDateTime.parse("2021-05-18T13:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-18T15:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "Tune in to find out about how we're furthering our mission to organize the world’s information and make it universally accessible and useful.",
//    ),
//    Event(
//        tags = listOf(),
//        title = "Developer Keynote",
//        color = 0xFFAFBBF2.toString(),
//        startTime = LocalDateTime.parse("2021-05-18T15:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-18T16:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "Learn about the latest updates to our developer products and platforms from Google Developers.",
//    ),
//    Event(
//        tags = listOf(),
//        title = "What's new in Android",
//        color = 0xFF1B998B.toString(),
//        startTime = LocalDateTime.parse("2021-05-18T17:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-18T21:15:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "In this Keynote, Chet Haase, Dan Sandler, and Romain Guy discuss the latest Android features and enhancements for developers.",
//    ),
//    Event(
//        tags = listOf(),
//        title = "What's new in Machine Learning",
//        color = 0xFFF4BFDB.toString(),
//        startTime = LocalDateTime.parse("2021-05-19T09:30:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-19T11:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "Learn about the latest and greatest in ML from Google. We’ll cover what’s available to developers when it comes to creating, understanding, and deploying models for a variety of different applications.",
//    ),
//    Event(
//        tags = listOf(),
//        title = "What's new in Material Design",
//        color = 0xFF6DD3CE.toString(),
//        startTime = LocalDateTime.parse("2021-05-19T11:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-19T12:15:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "Learn about the latest design improvements to help you build personal dynamic experiences with Material Design.",
//    ),
//    Event(
//        tags = listOf(),
//        title = "Jetpack Compose Basics",
//        color = 0xFF1B998B.toString(),
//        startTime = LocalDateTime.parse("2021-05-22T12:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-22T13:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "This Workshop will take you through the basics of building your first app with Jetpack Compose, Android's new modern UI toolkit that simplifies and accelerates UI development on Android.",
//    ),
//    Event(
//        tags = listOf(),
//        title = "Hello world presentation",
//        color = 0xFF1B998B.toString(),
//        startTime = LocalDateTime.parse("2021-05-23T15:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-23T16:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "This Workshop will take you through the basics of building your first app with Jetpack Compose, Android's new modern UI toolkit that simplifies and accelerates UI development on Android.",
//    ),
//    Event(
//        tags = listOf(),
//        title = "Goodbye iOS",
//        color = 0xFF1B998B.toString(),
//        startTime = LocalDateTime.parse("2021-05-23T13:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-23T14:45:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "This Workshop will take you through the basics of building your first app with Jetpack Compose, Android's new modern UI toolkit that simplifies and accelerates UI development on Android.",
//    ),
//    Event(
//        tags = listOf(),
//        title = "MacOS loses popularity",
//        color = 0xFF1B998B.toString(),
//        startTime = LocalDateTime.parse("2021-05-26T15:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        endTime = LocalDateTime.parse("2021-05-26T16:00:00").toInstant(
//            ZoneId.systemDefault().rules.getOffset(
//                Instant.now()
//            )
//        ).epochSecond,
//        description = "This Workshop will take you through the basics of building your first app with Jetpack Compose, Android's new modern UI toolkit that simplifies and accelerates UI development on Android.",
//    ),





@Composable
fun BasicSchedule(
    events: List<Event>,
    modifier: Modifier = Modifier,
    eventContent: @Composable (event: Event) -> Unit = { BasicEvent(event = it) },
    minDate: LocalDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(
        events.minByOrNull(Event::startTime)!!.startTime),
        ZoneId.systemDefault()
    ).toLocalDate(),
    maxDate: LocalDate = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(events.maxByOrNull(Event::endTime)!!.endTime),
        ZoneId.systemDefault()
    ).toLocalDate(),
    dayWidth: Dp,
    hourHeight: Dp,
) {
    val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
    val dividerColor = if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray
    Layout(
        content = {
            events.sortedBy(Event::startTime).forEach { event ->
                Box(modifier = Modifier.eventData(event)) {
                    eventContent(event)
                }
            }
        },
        modifier = modifier.drawBehind {
            repeat(23) {
                drawLine(
                    dividerColor,
                    start = Offset(0f, (it + 1) * hourHeight.toPx()),
                    end = Offset(size.width, (it + 1) * hourHeight.toPx()),
                    strokeWidth = 1.dp.toPx()
                )
            }
            repeat(numDays - 1) {
                drawLine(
                    dividerColor,
                    start = Offset((it + 1) * dayWidth.toPx(), 0f),
                    end = Offset((it + 1) * dayWidth.toPx(), size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
        },
    ) { measureables, constraints ->
        val height = hourHeight.roundToPx() * 24
        val width = dayWidth.roundToPx() * numDays
        val placeablesWithEvents = measureables.map { measurable ->
            val event = measurable.parentData as Event
            val eventDurationMinutes = ChronoUnit.MINUTES.between(Instant.ofEpochSecond(event.startTime)
                , Instant.ofEpochSecond(event.endTime))
            val eventHeight = ((eventDurationMinutes / 60f) * hourHeight.toPx()).roundToInt()
            val placeable = measurable.measure(
                constraints.copy(
                    minWidth = dayWidth.toPx().toInt(),
                    maxWidth = dayWidth.toPx().toInt(),
                    minHeight = eventHeight,
                    maxHeight = eventHeight
                )
            )

            Pair(placeable, event)
        }
        layout(width, height) {
            placeablesWithEvents.forEach { (placeable, event) ->
                val eventOffsetMinutes = ChronoUnit.MINUTES.between(
                    LocalTime.MIN, LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(event.startTime),
                        ZoneId.systemDefault()
                    ).toLocalTime()
                )
                val eventY = ((eventOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt()
                val eventOffsetDays = ChronoUnit.DAYS.between(
                    minDate, LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(event.startTime),
                        ZoneId.systemDefault()
                    ).toLocalDate()
                ).toInt()
                val eventX = eventOffsetDays * dayWidth.roundToPx()
                placeable.place(eventX, eventY)
            }
        }
    }
}




@Composable
fun Schedule(
    events: List<Event>,
    modifier: Modifier = Modifier,
    eventContent: @Composable (event: Event) -> Unit = { BasicEvent(event = it) },
    minDate: LocalDate = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(events.minByOrNull(Event::startTime)!!.startTime),
        ZoneId.systemDefault()
    ).toLocalDate(),
    maxDate: LocalDate = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(events.maxByOrNull(Event::endTime)!!.endTime),
        ZoneId.systemDefault()
    ).toLocalDate(),
) {
    val dayWidth = 256.dp
    val hourHeight = 48.dp
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()
    var sidebarWidth by remember { mutableStateOf(0) }

    Column(modifier = modifier) {
        ScheduleHeader(
            minDate = minDate,
            maxDate = maxDate,
            dayWidth = dayWidth,
            modifier = Modifier
                .padding(start = with(LocalDensity.current) { sidebarWidth.toDp() })
                .horizontalScroll(horizontalScrollState)
        )
        Row(modifier = Modifier.weight(1f)) {
            ScheduleSidebar(
                hourHeight = hourHeight,
                modifier = Modifier
                    .verticalScroll(verticalScrollState)
                    .onGloballyPositioned { sidebarWidth = it.size.width }
            )
            BasicSchedule(
                events = events,
                eventContent = eventContent,
                minDate = minDate,
                maxDate = maxDate,
                dayWidth = dayWidth,
                hourHeight = hourHeight,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(verticalScrollState)
                    .horizontalScroll(horizontalScrollState)
            )
        }
    }
}
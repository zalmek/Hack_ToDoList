package com.example.hack_todolist

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import com.example.hack_todolist.Models.Event

private class EventDataModifier(
    val event: Event,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = event
}

fun Modifier.eventData(event: Event) = this.then(EventDataModifier(event))



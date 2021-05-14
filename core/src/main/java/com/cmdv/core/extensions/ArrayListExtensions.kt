package com.cmdv.core.extensions

fun <T> ArrayList<T>.addAllNoRepeated(items: List<T>) {
    items.forEach { newElement ->
        if (!this.contains(newElement)) {
            this.add(newElement)
        }
    }
}
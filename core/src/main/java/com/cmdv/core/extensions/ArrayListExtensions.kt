package com.cmdv.core.extensions

import java.lang.reflect.Field

fun <T> ArrayList<T>.addAllNoRepeated(items: List<T>) {
    items.forEach { newElement ->
        if (!this.contains(newElement)) {
            this.add(newElement)
        }
    }
}

fun <T> ArrayList<Any>.addAllWithStartingLetter(items: List<T>, fieldName: String) {
    var declaredField: Field? = null
    try {
        items.sortedBy { generic ->
            val c: Class<out T> = generic!!::class.java

            c.declaredFields.forEach {
                it.isAccessible = true
                if (it.name == fieldName) declaredField = it
            }
            declaredField?.toGenericString() ?: ""
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        val i: MutableCollection<T> = mutableListOf()
        items.toCollection(i)

        val r: Collection<Any> = listOf()
        i.forEach {
            declaredField?.let { field ->
                if (!r.contains(field.toGenericString()[0])) {
                    r.plus(field.toGenericString()[0])
                }
            }
            r.plus(it)
        }

        this.addAll(r)
    }
}
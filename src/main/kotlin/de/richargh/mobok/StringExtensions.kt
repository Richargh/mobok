package de.richargh.mobok

fun <T> List<T>.subListOrEmpty(fromIndex: Int) = if (size <= fromIndex) emptyList()
else subList(fromIndex, size)
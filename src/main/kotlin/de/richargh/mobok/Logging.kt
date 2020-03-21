package de.richargh.mobok

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope

fun info(message: Any?) = println("[${Thread.currentThread().name}] $message")
fun CoroutineScope.info(message: Any?) = println("[${Thread.currentThread().name}@${coroutineContext[CoroutineName]?.name ?: "NoName"} ] $message")
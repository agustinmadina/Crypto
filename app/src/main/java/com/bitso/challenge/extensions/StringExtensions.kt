package com.bitso.challenge.extensions

fun String.capitalizeBook(): String = split("_").map { it.toUpperCase() }.joinToString("/")

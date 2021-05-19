package com.bitso.challenge.coroutines

import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {

    fun main(): CoroutineContext

    fun io(): CoroutineContext

    fun default(): CoroutineContext
}
package com.bitso.challenge.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DispatcherProviderImpl : DispatcherProvider {

    override fun main(): CoroutineContext = Dispatchers.Main

    override fun io(): CoroutineContext = Dispatchers.IO

    override fun default(): CoroutineContext = Dispatchers.Default
}
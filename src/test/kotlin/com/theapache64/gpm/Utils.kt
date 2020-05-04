package com.theapache64.gpm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

fun runBlockingUnitTest(block: suspend (scope: CoroutineScope) -> Unit) = runBlocking {
    block(this)
    Unit
}
package com.theapache64.gpm.commands.base

import com.theapache64.gpm.utils.GpmConfig
import com.theapache64.gpm.utils.InputUtils
import kotlinx.coroutines.delay
import java.util.concurrent.Callable

abstract class BaseCommand<T> : Callable<T> {

    suspend fun chooseIndex(items: List<String>): Int {

        println("Choose: ")

        items.forEachIndexed() { index: Int, string: String ->
            println("${index + 1}) $string")
        }

        @Suppress("ConstantConditionIf")
        return if (GpmConfig.IS_DEBUG_MODE) {
            delay(1000)
            1
        } else {
            InputUtils.getInt("Choose one", 1, items.size) - 1
        }
    }
}
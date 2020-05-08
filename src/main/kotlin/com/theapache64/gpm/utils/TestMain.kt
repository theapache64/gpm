package com.theapache64.gpm.utils

import me.tongfei.progressbar.ProgressBarBuilder
import me.tongfei.progressbar.ProgressBarStyle

fun main(args: Array<String>) {
    val pb = ProgressBarBuilder()
        .setInitialMax(100)
        .setStyle(ProgressBarStyle.COLORFUL_UNICODE_BLOCK)
        .setTaskName("Print task")
        .setUpdateIntervalMillis(100)
        .build()
    pb.use {
        for (name in (0..100)) {
            it.extraMessage = "Printing $name"
            Thread.sleep(50)
            it.step()
        }
    }
}
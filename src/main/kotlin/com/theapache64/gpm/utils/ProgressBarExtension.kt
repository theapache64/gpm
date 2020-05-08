package com.theapache64.gpm.utils

import me.tongfei.progressbar.ProgressBar

fun ProgressBar.set(stepTo: Long, msg: String) {
    stepTo(stepTo)
    extraMessage = msg
}
package com.theapache64.gpm.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtils {
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
}
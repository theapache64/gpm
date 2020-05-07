package com.theapache64.gpm.commands.base

abstract class BaseViewModel<T> {
    abstract suspend fun call(command: T) : Int
}
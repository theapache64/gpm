package com.theapache64.gpm.commands.gpm

import com.theapache64.gpm.commands.base.BaseViewModel
import javax.inject.Inject

class GpmViewModel @Inject constructor() : BaseViewModel<Gpm>() {
    override suspend fun call(command: Gpm): Int {
        return 0
    }
}
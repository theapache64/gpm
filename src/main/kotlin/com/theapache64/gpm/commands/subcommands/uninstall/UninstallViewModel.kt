package com.theapache64.gpm.commands.subcommands.uninstall

import com.theapache64.gpm.commands.base.BaseViewModel
import javax.inject.Inject

class UninstallViewModel @Inject constructor(

) : BaseViewModel<Uninstall>() {

    override suspend fun call(command: Uninstall): Int {
        TODO("Not yet implemented")
    }

}
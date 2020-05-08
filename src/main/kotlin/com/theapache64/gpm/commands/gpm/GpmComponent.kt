package com.theapache64.gpm.commands.gpm

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface GpmComponent {
    fun inject(gpm: Gpm)
}
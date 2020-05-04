package com.theapache64.gpm.rules

import com.theapache64.gpm.di.components.InstallComponent
import com.theapache64.gpm.di.modules.NetworkModule
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.DaggerMockRule

class MyDaggerMockRule : DaggerMockRule<InstallComponent>(
    InstallComponent::class.java,
    NetworkModule()
)
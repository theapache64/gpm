package com.theapache64.gpm.rules

import com.theapache64.gpm.di.components.InstallComponent
import it.cosenonjaviste.daggermock.DaggerMockRule

class MyDaggerMockRule : DaggerMockRule<InstallComponent>(InstallComponent::class.java) {

}
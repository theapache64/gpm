package com.theapache64.gpm.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class GradleFile

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class GpmJsonFile

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class InstallProgress
package com.masiad.arescaperoom.util.model

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object ModelLoaderModule {

    @FragmentScoped
    @Provides
    fun provideModelLoader(@ActivityContext context: Context): ModelLoader =
        ModelLoaderImpl(context)
}
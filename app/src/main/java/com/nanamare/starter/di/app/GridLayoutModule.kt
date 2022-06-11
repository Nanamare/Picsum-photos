package com.nanamare.starter.di.app

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.nanamare.starter.R
import com.nanamare.starter.util.getOptimizedSpanCount
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Provider

@InstallIn(ActivityComponent::class)
@Module
class GridLayoutModule {

    @ActivityScoped
    @Provides
    fun provideGridLayoutManager(
        @ActivityContext context: Context,
        @Named("span_count") spanCount: Provider<Int>
    ): GridLayoutManager = GridLayoutManager(context, spanCount.get())

}

@InstallIn(SingletonComponent::class)
@Module
class ResourceModule {

    @Provides
    @Named("span_count")
    fun provideSpanCount(@ApplicationContext context: Context): Int =
        getOptimizedSpanCount(context, R.dimen.image_min_width)

    @Provides
    @Named("page_count")
    fun providePageCount(@Named("span_count") spanCountProvider: Provider<Int>): Int =
        spanCountProvider.get() * DEFAULT_PAGE_SIZE_FACTOR

    companion object {
        const val DEFAULT_PAGE_SIZE_FACTOR = 7
    }
}
package com.example.testlastfm.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.testlastfm.ui.albumDetails.AlbumDetailsViewModel
import com.example.testlastfm.ui.searchAlbums.SearchAlbumViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Used for injecting View Models
 */
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(SearchAlbumViewModel::class)
    internal abstract fun postListViewModel(viewModel: SearchAlbumViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel::class)
    internal abstract fun postAlbumDetailsViewModel(viewModel: AlbumDetailsViewModel): ViewModel

    //Add more ViewModels here


}
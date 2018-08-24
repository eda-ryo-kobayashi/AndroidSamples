package jp.eda_inc.user

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */

@Module(includes = [ProvideViewModel::class])
abstract class MainModule {

  @Provides
  fun providesMainDispatcher() = MainDispatcher()

  @Provides
  fun provideMainViewModel(factory: ViewModelProvider.Factory, target: MainActivity) =
      ViewModelProviders.of(target, factory).get(MainViewModel::class.java)

}

@Module
abstract class ProvideViewModel {

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}

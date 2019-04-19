package jp.eda_inc.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */

@Module(includes = [ViewModelModule::class])
class MainModule {

  @Provides
  fun providesMainDispatcher(): MainDispatcher = MainDispatcherImpl()

}

@Module
abstract class ViewModelModule {

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}
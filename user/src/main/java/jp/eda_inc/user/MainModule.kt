package jp.eda_inc.user

import dagger.Module
import dagger.Provides

/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */

@Module
class MainModule {

  @Provides
  fun providesMainDispatcher() = MainDispatcher()

  // TODO ViewModelFactory

}
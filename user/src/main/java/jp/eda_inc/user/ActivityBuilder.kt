package jp.eda_inc.user

import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */
@Module
abstract class ActivityBuilder {

  @ContributesAndroidInjector(modules = [MainModule::class])
  abstract fun contributesActivityMain(): MainActivity
}
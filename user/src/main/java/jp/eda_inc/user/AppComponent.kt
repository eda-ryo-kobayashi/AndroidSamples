package jp.eda_inc.user

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */
@Singleton
@Component(modules = [
  AndroidSupportInjectionModule::class,
  MainModule::class
])
interface AppComponent {

  fun inject(app: App)

  @Component.Builder
  interface Builder {
    // 必要なインスタンスがあるなら追加
    @BindsInstance
    fun application(app: App): Builder

    fun build(): AppComponent
  }

}

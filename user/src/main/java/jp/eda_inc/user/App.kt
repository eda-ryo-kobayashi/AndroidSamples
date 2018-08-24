package jp.eda_inc.user

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */
class App : Application(), HasActivityInjector {

  @Inject
  lateinit var daInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()

    DaggerAppComponent.builder().application(this).build().inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = daInjector

}
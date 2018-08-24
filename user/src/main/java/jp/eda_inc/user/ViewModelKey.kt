package jp.eda_inc.user

import android.arch.lifecycle.ViewModel
import kotlin.reflect.KClass

/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */
annotation class ViewModelKey(val value: KClass<out ViewModel>)
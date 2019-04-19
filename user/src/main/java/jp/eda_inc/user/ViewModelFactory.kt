package jp.eda_inc.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.NonNull
import javax.inject.Inject
import javax.inject.Provider


/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */
class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

  @NonNull
  override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
    var creator: Provider<ViewModel>? = creators[modelClass]

    if (creator == null) {
      for ((key, value) in creators) {
        if (modelClass.isAssignableFrom(key)) {
          creator = value
        }
      }
    }

    if (creator == null) {
      throw IllegalArgumentException("Unkown model class$modelClass")
    }

    try {
      return creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }

  }

}

package jp.eda_inc.user

import io.reactivex.processors.FlowableProcessor

/**
 * Created by RyoKobayashi on 2018/08/24
 *
 */
interface MainDispatcher {

  val onEvent: FlowableProcessor<Action>
}
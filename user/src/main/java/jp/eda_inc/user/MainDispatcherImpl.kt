package jp.eda_inc.user

import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor

/**
 * Created by RyoKobayashi on 2018/08/28
 *
 */
class MainDispatcherImpl : MainDispatcher {

  override val onEvent: FlowableProcessor<Action> = BehaviorProcessor.create<Action>().toSerialized()
}
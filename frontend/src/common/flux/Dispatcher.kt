package flux

import common.flux.Store

object Dispatcher {

    private var subscribers = ArrayList<Store>()

    fun dispatch(action: Action, payload: Any) {
        subscribers.forEach {
            it.dispatch(action, payload)
        }
    }

    fun subscribe(subscriber: Store) {
        subscribers.add(subscriber)
    }
}
package common.flux

import flux.Action

abstract class Store {


    protected var subscribers: MutableMap<Long, () -> Unit> = HashMap()
    private var idSequence: Long = 0

    abstract fun dispatch(action: Action, payload: Any)

    fun subscribe(callback: () -> Unit): Long {
        subscribers[++idSequence] = callback
        return idSequence
    }

    fun unsubscribe(id: Long) {
        subscribers.remove(id)
    }
}
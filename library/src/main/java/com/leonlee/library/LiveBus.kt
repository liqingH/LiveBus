package com.leonlee.library

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.leonlee.library.LiveBus.ObserverWrapper
import java.util.concurrent.ConcurrentHashMap

/**
 * 使用LiveData实现的事件总线
 *
 * User：Leon Lee
 * Date：2018/12/30
 *
 * @vision 1.0
 */
class LiveBus private constructor() {

    private val busMap by lazy { ConcurrentHashMap<Class<*>, BusLiveData<*>>() }

    private fun <T> bus(clazz: Class<T>) = busMap.getOrPut(clazz) { BusLiveData<T>() }

    fun <T> with(clazz: Class<T>) = bus(clazz) as BusLiveData<T>

    class BusLiveData<T> : MutableLiveData<T>() {

        var mVersion = START_VERSION

        var mStickyEvent: T? = null

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            observe(owner, observer, false)
        }

        fun observe(owner: LifecycleOwner, observer: Observer<T>, sticky: Boolean) {
            super.observe(owner, ObserverWrapper(observer, this, sticky))
        }

        override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
            mVersion++
            super.postValue(value)
        }

        fun setValueSticky(value: T) {
            mStickyEvent = value
            setValue(value)
        }

        fun postValueSticky(value: T) {
            mStickyEvent = value
            postValue(value)
        }

        fun removeSticky() {
            mStickyEvent = null
        }
    }

    private class ObserverWrapper<T>(val observer: Observer<T>, val liveData: BusLiveData<T>, val sticky: Boolean) : Observer<T> {

        // 通过标志位过滤旧数据
        private var mLastVersion = liveData.mVersion

        override fun onChanged(t: T?) {

            if (mLastVersion >= liveData.mVersion) {
                // 回调粘性事件
                if (sticky && liveData.mStickyEvent != null) {
                    observer.onChanged(liveData.mStickyEvent)
                }
                return
            }
            mLastVersion = liveData.mVersion

            observer.onChanged(t)
        }
    }

    companion object {

        private const val START_VERSION = -1

        @Volatile
        private var instance: LiveBus? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: LiveBus().also { instance = it }
        }

    }

}
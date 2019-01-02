package com.leonlee.library

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

/**
 *
 *
 * User：liqinghai
 * Date：2019/1/2
 *
 * @vision 1.0
 */
class BusLiveData<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, Observer<T>{
            
        })
    }
}
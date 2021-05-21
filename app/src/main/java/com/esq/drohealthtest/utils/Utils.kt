package com.esq.drohealthtest.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.arch.core.util.Function
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Shows a short Toast with a String Parameter.
 */
fun Context.shortToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 * Shows a short Toast with an Int(Resource Value) Parameter.
 */
fun Context.shortToast(@StringRes msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 * Shows a long Toast with a String Parameter.
 */
fun Context.longToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

/**
 * Shows a long Toast with an Int(Resource Value) Parameter.
 */
fun Context.longToast(@StringRes msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

/**
 * A generic extension utility method for observing once.
 */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

/**
 * A generic extension utility method for converting a list to a flow.
 */
fun <T> List<T>.toFlow(): Flow<List<T>> =
    flow {
        while (true) {
            emit(this@toFlow)
            delay(1000)
        }
    }

/**
 * An extension utility method that works like switch map of the Transformations class but returns an int.
 */
@MainThread
fun <X> MediatorLiveData<Int>.switchMapThenComputeIntValueType(
    source: LiveData<X>,
    switchMapFunction: Function<X?, Int>
): LiveData<Int?> {
    val result = this
    result.addSource(source
    ) { x -> result.value = switchMapFunction.apply(x) }
    return result
}


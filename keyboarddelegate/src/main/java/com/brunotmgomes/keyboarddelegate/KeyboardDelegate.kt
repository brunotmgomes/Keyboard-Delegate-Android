package com.brunotmgomes.keyboarddelegate

import android.app.Activity
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class KeyboardDelegate private constructor(private var activity: Activity) {

    companion object {
        const val ADJUST_RESIZE = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        const val ADJUST_PAN = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN

        private var instance: KeyboardDelegate? = null

        /**
         * Initialize the keyboard delegate for an Activity
         */
        fun initialize(activity: Activity) {
            instance = KeyboardDelegate(activity)
        }

        /**
         * Gets an existing instance of the keyboard override.
         * A different activity can be set for multiple acitivity scenarios
         */
        fun getInstance(activity: Activity? = null): KeyboardDelegate {
            if (activity != null) {
                instance!!.activity = activity
            }
            return instance!!
        }
    }

    private var defaultInputMethod: Int = -1
    private var overrideObserver: LifecycleObserver? = null
    private var overrideOwner: LifecycleOwner? = null

    /**
     * Overrides keyboard input Style
     * There can only be one override at a time
     */
    fun setInputModeOverride(lifecycleOwner: LifecycleOwner, overrideMode: Int) {
        if (overrideOwner != null) {
            overrideObserver?.let {
                overrideOwner!!.lifecycle.removeObserver(it)
            }
        } else {
            defaultInputMethod = activity.window.attributes.softInputMode
        }
        overrideOwner = lifecycleOwner
        overrideObserver = object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun addOverride() {
                activity.window?.setSoftInputMode(overrideMode)
            }

            // If no one else overrides; set default input method on pause
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun removeOverride() {
                activity.window?.setSoftInputMode(defaultInputMethod)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun destroy() {
                lifecycleOwner.let {
                    lifecycleOwner.lifecycle.removeObserver(this)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(overrideObserver!!)
    }

}
package com.yandex.mobile.ads.flutter.util

import android.content.Context
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import java.lang.ref.WeakReference

internal class ActivityContextHolder : ActivityAware {

    val activityContext: Context?
        get() = activityContextReference?.get()

    private var activityContextReference: WeakReference<Context>? = null

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activityContextReference = WeakReference(binding.activity)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activityContextReference?.clear()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activityContextReference = WeakReference(binding.activity)
    }

    override fun onDetachedFromActivity() {
        activityContextReference?.clear()
    }
}

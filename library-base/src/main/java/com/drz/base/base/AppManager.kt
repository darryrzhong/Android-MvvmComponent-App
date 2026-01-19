package com.drz.base.base

import android.app.Activity
import java.util.Stack

/**
 * 应用模块:
 *
 * 类描述: Activity的堆栈管理者
 *
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
class AppManager private constructor() {

    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
        }
    }

    val isActivity: Boolean
        get() = activityStack != null && !activityStack!!.isEmpty()

    fun currentActivity(): Activity {
        return activityStack!!.lastElement()
    }

    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    fun finishAllActivity() {
        for (i in activityStack!!.indices) {
            if (null != activityStack!![i]) {
                finishActivity(activityStack!![i])
            }
        }
        activityStack!!.clear()
    }

    fun getActivity(cls: Class<*>): Activity? {
        if (activityStack != null) {
            for (activity in activityStack!!) {
                if (activity.javaClass == cls) {
                    return activity
                }
            }
        }
        return null
    }

    fun AppExit() {
        try {
            finishAllActivity()
        } catch (e: Exception) {
            activityStack!!.clear()
            e.printStackTrace()
        }
    }

    private object SingleHolder {
        val instance = AppManager()
    }

    companion object {
        private var activityStack: Stack<Activity>? = null
        val instance: AppManager
            get() = SingleHolder.instance
    }
}

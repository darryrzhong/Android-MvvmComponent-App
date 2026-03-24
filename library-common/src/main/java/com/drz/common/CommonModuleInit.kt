package com.drz.common

import com.drz.base.base.BaseApplication
import com.tencent.mmkv.MMKV

class CommonModuleInit : IModuleInit {
    override fun onInitAhead(application: BaseApplication?): Boolean {
        application?.let { MMKV.initialize(it) }
        return true
    }

    override fun onInitLow(application: BaseApplication?): Boolean = true
}

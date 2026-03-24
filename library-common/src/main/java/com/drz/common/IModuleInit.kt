package com.drz.common

import com.drz.base.base.BaseApplication

interface IModuleInit {
    fun onInitAhead(application: BaseApplication?): Boolean
    fun onInitLow(application: BaseApplication?): Boolean
}

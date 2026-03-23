package com.drz.main.application

import com.drz.base.base.BaseApplication
import com.drz.common.IModuleInit

class MainModuleInit : IModuleInit {
    override fun onInitAhead(application: BaseApplication?): Boolean = true
    override fun onInitLow(application: BaseApplication?): Boolean = true
}

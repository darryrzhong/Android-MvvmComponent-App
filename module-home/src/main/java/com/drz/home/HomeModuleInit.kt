package com.drz.home

import com.drz.base.base.BaseApplication
import com.drz.common.IModuleInit

class HomeModuleInit : IModuleInit {
    override fun onInitAhead(application: BaseApplication?): Boolean = true
    override fun onInitLow(application: BaseApplication?): Boolean = true
}

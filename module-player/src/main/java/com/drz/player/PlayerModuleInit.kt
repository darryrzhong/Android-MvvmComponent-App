package com.drz.player

import com.drz.base.base.BaseApplication
import com.drz.common.IModuleInit

class PlayerModuleInit : IModuleInit {
    override fun onInitAhead(application: BaseApplication?): Boolean = true
    override fun onInitLow(application: BaseApplication?): Boolean = true
}

package com.theapache64.gpm.core.registries.gpm

import com.theapache64.gpm.core.base.BaseRegistry
import com.theapache64.gpm.models.Dependency
import java.net.URLEncoder

object GpmRegistry : BaseRegistry {

    override fun getDependencyUrl(name: String): String {
        val nameEnc = URLEncoder.encode(name, "UTF-8")
        return "https://raw.githubusercontent.com/theapache64/gpm/master/registry/$nameEnc.json"
    }

    override fun getDependency(name: String): Dependency? {
        val url = getDependencyUrl(name)

        return null
    }
}
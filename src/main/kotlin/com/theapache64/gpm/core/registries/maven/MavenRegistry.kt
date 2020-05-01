package com.theapache64.gpm.core.registries.maven

import com.theapache64.gpm.core.base.BaseRegistry
import com.theapache64.gpm.models.Dependency
import java.net.URLEncoder

object MavenRegistry : BaseRegistry {

    override fun getDependencyUrl(name: String): String {
        val nameEnc = URLEncoder.encode(name, "UTF-8")
        return "https://search.maven.org/solrsearch/select?q=${nameEnc}okhttp&rows=1&wt=json"
    }

    override fun getDependency(name: String): Dependency? {
        TODO("Not yet implemented")
    }
}
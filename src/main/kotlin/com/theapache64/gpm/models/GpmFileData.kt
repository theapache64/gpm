package com.theapache64.gpm.models

import com.squareup.moshi.Json
import com.theapache64.gpm.core.gm.GradleDependency
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency

class GpmFileData(
    @Json(name = "added")
    val deps: MutableList<AddedDep>
) {
    class AddedDep(
        @Json(name = "type")
        val type: String,
        @Json(name = "gpm_dep")
        val gpmDependency: GpmDependency
    )
}
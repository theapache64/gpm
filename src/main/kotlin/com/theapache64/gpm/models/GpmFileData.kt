package com.theapache64.gpm.models

import com.squareup.moshi.Json
import com.theapache64.gpm.data.remote.gpm.models.GpmDep

class GpmFileData(
    @Json(name = "added")
    val deps: MutableList<AddedDep>
) {
    class AddedDep(
        @Json(name = "type")
        val type: String,
        @Json(name = "installed_name")
        val installedName: String,
        @Json(name = "gpm_dep")
        val gpmDep: GpmDep
    )
}
package com.theapache64.gpm.core

import com.squareup.moshi.Moshi
import com.theapache64.gpm.core.gm.GradleDependency
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import com.theapache64.gpm.models.GpmFileData
import com.theapache64.gpm.utils.GpmConfig
import java.io.File

class GpmFileManager(
    private val moshi: Moshi
) {
    companion object {
        private val GPM_FILE by lazy {
            @Suppress("ConstantConditionIf")
            if (GpmConfig.IS_DEBUG_MODE) {
                File("assets/temp.gpm.json")
            } else {
                File("gpm.json")
            }
        }
    }

    fun add(type: GradleDependency.Type, newGpmDep: GpmDependency) {
        // Need to login
        val adapter = moshi.adapter(GpmFileData::class.java).indent(" ")
        val depToStore = GpmFileData.AddedDep(
            type.keyword,
            newGpmDep
        )
        val newFileData = if (!GPM_FILE.exists()) {
            GpmFileData(mutableListOf(depToStore))
        } else {
            val gpmFileData = adapter.fromJson(GPM_FILE.readText())
            gpmFileData!!.deps.add(depToStore)
            gpmFileData
        }


        val gpmFileDataJson = adapter
            .toJson(newFileData)

        GPM_FILE.writeText(gpmFileDataJson)
    }
}
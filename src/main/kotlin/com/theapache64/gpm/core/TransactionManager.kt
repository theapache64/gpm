package com.theapache64.gpm.core

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.models.GpmFileData
import com.theapache64.gpm.utils.GpmConfig
import java.io.File
import javax.inject.Inject

class TransactionManager @Inject constructor(
    private val moshi: Moshi
) {

    private val adapter: JsonAdapter<GpmFileData> by lazy {
        moshi.adapter(GpmFileData::class.java).indent(" ")
    }

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

    fun add(installedName: String, type: GradleDep.Type, newGpmDep: GpmDep) {
        // Need to login

        val depToStore = GpmFileData.AddedDep(
            type.keyword,
            installedName,
            newGpmDep
        )
        val newFileData = if (!GPM_FILE.exists()) {
            GpmFileData(mutableListOf(depToStore))
        } else {
            val gpmFileData = adapter.fromJson(GPM_FILE.readText())
            gpmFileData!!.deps.add(depToStore)
            gpmFileData
        }


        val gpmFileDataJson = adapter.toJson(newFileData)

        GPM_FILE.writeText(gpmFileDataJson)
    }

    fun getInstalled(depName: String): List<GpmFileData.AddedDep> {
        val gpmFileData = adapter.fromJson(GPM_FILE.readText())!!
        return gpmFileData.deps.filter {
            it.installedName == depName
        }
    }
}
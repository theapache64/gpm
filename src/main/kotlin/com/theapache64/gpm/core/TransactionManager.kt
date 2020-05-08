package com.theapache64.gpm.core

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.models.GpmFileData
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

class TransactionManager @Inject constructor(
    private val gpmJsonFile: File,
    private val moshi: Moshi
) {

    private val adapter: JsonAdapter<GpmFileData> by lazy {
        moshi.adapter(GpmFileData::class.java).indent(" ")
    }


    fun add(installedName: String, type: GradleDep.Type, newGpmDep: GpmDep) {

        // Need to login
        val newDepId = getLastDepAdded()?.id?.plus(1) ?: 1

        if (newDepId != 1) {
            // got prev transaction, so check if this is a duplicate one
            if (isDuplicate(installedName, type, newGpmDep)) {
                return
            }
        }

        val depToStore = GpmFileData.AddedDep(
            newDepId,
            type.key,
            installedName,
            newGpmDep
        )
        val newFileData = if (!gpmJsonFile.exists()) {
            GpmFileData(mutableListOf(depToStore))
        } else {
            val gpmFileData = adapter.fromJson(gpmJsonFile.readText())
            gpmFileData!!.deps.add(depToStore)
            gpmFileData
        }

        setData(newFileData)
    }

    private fun isDuplicate(installedName: String, type: GradleDep.Type, newGpmDep: GpmDep): Boolean {
        return getData()?.deps?.find {
            it.installedName == installedName &&
                    it.type == type.key &&
                    it.gpmDep.artifactId == newGpmDep.artifactId &&
                    it.gpmDep.groupId == newGpmDep.groupId &&
                    it.gpmDep.name == newGpmDep.name
        } != null
    }

    private fun getLastDepAdded(): GpmFileData.AddedDep? {
        return try {
            getData()?.deps?.lastOrNull()
        } catch (e: FileNotFoundException) {
            null
        }
    }

    private fun setData(newFileData: GpmFileData) {
        val gpmFileDataJson = adapter.toJson(newFileData)
        gpmJsonFile.writeText(gpmFileDataJson)
    }

    fun getInstalled(type: String?, depName: String): List<GpmFileData.AddedDep> {
        return getData()?.deps?.filter {
            it.installedName == depName && (type == null || it.type == type)
        } ?: listOf()
    }

    fun remove(depToRemove: GpmFileData.AddedDep) {
        val data = getData()
        if (data != null) {
            val isRemoved = data.deps.removeIf { it.id == depToRemove.id }
            if (isRemoved) {
                setData(data)
            } else {
                throw IOException("Failed to remove dependency. Couldn't find dependency with id '${depToRemove.id}'")
            }
        } else {
            throw IOException(
                """
                Failed to remove dependency. Are you sure you have installed it through gpm?
            """.trimIndent()
            )
        }
    }

    private fun getData() = try {
        adapter.fromJson(gpmJsonFile.readText())
    } catch (e: FileNotFoundException) {
        null
    }
}
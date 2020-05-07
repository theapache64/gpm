package com.theapache64.gpm.commands.subcommands.docs

import com.theapache64.gpm.commands.base.BaseViewModel
import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.data.repos.GpmRepo
import javax.inject.Inject

class DocsViewModel @Inject constructor(
    private val tm: TransactionManager,
    private val gpmRepo: GpmRepo
) : BaseViewModel<Docs>() {

    companion object {
        const val RESULT_DOC_FOUND = 123
        const val RESULT_NOT_FOUND = 404
    }

    override suspend fun call(command: Docs): Int {

        val depName = command.depName.trim().toLowerCase()
        val transaction = tm.getInstalled(null, depName)

        if (transaction.isEmpty()) {
            // check if remote registry got info about the repo in question
            val remoteDep = gpmRepo.getDep(depName) ?: return RESULT_NOT_FOUND

            command.onDocUrl(remoteDep.docs)
            return RESULT_DOC_FOUND

        } else {

            val index = command.chooseIndex(transaction.map {
                "${it.type} ${it.gpmDep.groupId}:${it.gpmDep.artifactId}"
            })

            val selDep = transaction[index]
            command.onDocUrl(selDep.gpmDep.docs)

            return RESULT_DOC_FOUND
        }
    }

}
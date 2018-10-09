package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.EntryType
import org.darkenk.worktimer.domain.Repository

class GetCurrentWorkStatus(private val repository: Repository) {

    fun isWorking(): Boolean {
        val entry = repository.getLastEntry()
        return entry.type == EntryType.WorkStart
    }
}
package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.EntryType
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.TimeProvider
import org.darkenk.worktimer.domain.WorkEntry

class EndWork(private val timeProvider: TimeProvider, private val repository: Repository) {
    
    fun endWork() {
        val lastEntry = repository.getLastEntry()
        if (lastEntry.type != EntryType.WorkStart) {
            throw StartWork.StartWorkException("Last Entry is $lastEntry")
        }
        val currentTime = timeProvider.getCurrentTime()
        if (currentTime < lastEntry.time) {
            throw StartWork.StartWorkException("Current time is lower than last entry $lastEntry")
        }
        repository.save(WorkEntry(EntryType.WorkEnd, currentTime))
    }
}


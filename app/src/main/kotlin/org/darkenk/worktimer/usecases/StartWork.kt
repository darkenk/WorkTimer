package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.EntryType
import java.lang.RuntimeException
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.TimeProvider
import org.darkenk.worktimer.domain.WorkEntry

class StartWork(private val timeProvider: TimeProvider, private val repository: Repository) {
    
    class StartWorkException(override val message: String): RuntimeException(message)
    
    fun startWork() {
        val lastEntry = repository.getLastEntry()
        if (lastEntry.type == EntryType.WorkStart) {
            throw StartWorkException("Last Entry is $lastEntry")
        }
        val currentTime = timeProvider.getCurrentTime()
        if (currentTime < lastEntry.time) {
            throw StartWorkException("Current time is lower than last entry $lastEntry")
        }
        repository.save(WorkEntry(EntryType.WorkStart, currentTime))
    }
}

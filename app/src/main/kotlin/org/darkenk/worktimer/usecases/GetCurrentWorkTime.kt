package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.EntryType
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.TimeProvider
import org.darkenk.worktimer.domain.WorkEntry
import java.lang.RuntimeException

class GetCurrentWorkTime(private val timeProvider: TimeProvider, private val repository: Repository) {

    class GetCurrentWorkException(message: String): RuntimeException(message)

    @Throws(GetCurrentWorkException::class)
    fun getWorkTime() : Long {
        val currentTime = timeProvider.getCurrentTime()
        val day = 86_400_000 
        val entries = repository.getEntries(currentTime - (currentTime % day), currentTime)

        var sum = 0L
        var start = 0L
        for (entry in entries) {
            if (entry.type == EntryType.WorkStart) {
                start = entry.time
            }
            if (0L != start && entry.type == EntryType.WorkEnd) {
                sum += entry.time - start
                start = 0L 
            }
        }
        if (start != 0L) {
            sum += currentTime - start
        }
        return sum
    }
}

package org.darkenk.worktimer.data

import org.darkenk.worktimer.domain.EntryType
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.WorkEntry

class InMemoryRepository : Repository {
    
    private val workEntries = mutableListOf<WorkEntry>()
    
    override fun getLastEntry(): WorkEntry {
        if (workEntries.isEmpty()) {
            return WorkEntry(EntryType.Empty, 0)
        }
        return workEntries.last()
    }

    override fun save(workEntry: WorkEntry) {
        workEntries.add(workEntry)
    }
}
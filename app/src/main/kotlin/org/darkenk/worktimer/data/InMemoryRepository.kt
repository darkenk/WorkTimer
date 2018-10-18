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
    
    override fun getEntries(from: Long, to: Long): List<WorkEntry> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
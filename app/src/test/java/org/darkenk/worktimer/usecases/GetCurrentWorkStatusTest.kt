package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.EntryType
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.WorkEntry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetCurrentWorkStatusTest {
    
    @Mock
    lateinit var repository: Repository
    
    lateinit var getCurrentWorkStatus: GetCurrentWorkStatus
    
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getCurrentWorkStatus = GetCurrentWorkStatus(repository)
    }
    
    @Test
    fun isWorkingReturnsTrueIfWorkWasSet() {
        `when`(repository.getLastEntry()).thenReturn(WorkEntry(EntryType.WorkStart, 100))
        
        assertTrue(getCurrentWorkStatus.isWorking())
    }

    @Test
    fun isWorkingReturnsFalseIfWorkWasNotSet() {
        `when`(repository.getLastEntry()).thenReturn(WorkEntry(EntryType.WorkEnd, 100))

        assertFalse(getCurrentWorkStatus.isWorking())
    }
}
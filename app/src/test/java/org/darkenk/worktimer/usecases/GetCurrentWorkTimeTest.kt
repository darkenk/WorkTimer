package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.EntryType
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.TimeProvider
import org.darkenk.worktimer.domain.WorkEntry
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetCurrentWorkTimeTest {
    
    @Mock
    lateinit var timeProvider: TimeProvider
    
    @Mock
    lateinit var repository: Repository
    
    private lateinit var worktime: GetCurrentWorkTime
    
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        worktime = GetCurrentWorkTime(timeProvider, repository)
    }
    
    @Test
    fun countWorkIfWorkWasNotEnded() {
        `when`(timeProvider.getCurrentTime()).thenReturn(90L)
        `when`(repository.getEntries(eq(0L), eq(90L)))
                .thenReturn(arrayListOf(WorkEntry(EntryType.WorkStart, 10L)))
        
        assertEquals(80L, worktime.getWorkTime())
    }

    @Test
    fun countWorkIfWorkWasEnded() {
        `when`(timeProvider.getCurrentTime()).thenReturn(90L)
        `when`(repository.getEntries(eq(0L), eq(90L)))
                .thenReturn(arrayListOf(
                        WorkEntry(EntryType.WorkStart, 10L),
                        WorkEntry(EntryType.WorkEnd, 80L)))
        
        assertEquals(70L, worktime.getWorkTime())
    }
    
    @Test
    fun countWorkIfWorkWasNotContinuousAndNotEnded() {
        `when`(timeProvider.getCurrentTime()).thenReturn(90L)
        `when`(repository.getEntries(eq(0L), eq(90L)))
                .thenReturn(arrayListOf(
                        WorkEntry(EntryType.WorkStart, 10L),
                        WorkEntry(EntryType.WorkEnd, 20L),
                        WorkEntry(EntryType.WorkStart, 40L)))

        assertEquals(60L, worktime.getWorkTime())
    }

    @Test
    fun countWorkIfWorkWasNotContinuousAndWasEnded() {
        `when`(timeProvider.getCurrentTime()).thenReturn(90L)
        `when`(repository.getEntries(eq(0L), eq(90L)))
                .thenReturn(arrayListOf(
                        WorkEntry(EntryType.WorkStart, 10L),
                        WorkEntry(EntryType.WorkEnd, 20L),
                        WorkEntry(EntryType.WorkStart, 40L),
                        WorkEntry(EntryType.WorkEnd, 50L)))

        assertEquals(20L, worktime.getWorkTime())
    }
    
    @Test
    fun getCurrentWorkTimeAsksOnlyForEntriesFromCurrentDay() {
        val day = 86_400_000L
        `when`(timeProvider.getCurrentTime()).thenReturn(2 * day + 90L)
        
        worktime.getWorkTime()
        
        verify(repository).getEntries(2 * day, 2 * day + 90L)
    }
    
}


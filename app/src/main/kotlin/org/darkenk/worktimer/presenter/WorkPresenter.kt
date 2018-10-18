package org.darkenk.worktimer.presenter

import org.darkenk.worktimer.usecases.EndWork
import org.darkenk.worktimer.usecases.GetCurrentWorkStatus
import org.darkenk.worktimer.usecases.GetCurrentWorkTime
import org.darkenk.worktimer.usecases.StartWork
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.error
import org.jetbrains.anko.uiThread
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer

class WorkPresenter(private val endWork: EndWork,
                    private val getCurrentWorkStatus: GetCurrentWorkStatus,
                    private val startWork: StartWork,
                    private val getCurrentWorkTime: GetCurrentWorkTime,
                    private val view: IMainView): AnkoLogger {
    
    private var isWorking: Boolean = false
    
    fun init() {
        fixedRateTimer("time udpater", false, Calendar.getInstance().time, period = 1000) {
            updateCurrentWorkingTime()
        }
        doAsync {
            isWorking = getCurrentWorkStatus.isWorking()
            uiThread { 
                view.showIsWorking(isWorking)
            }
        }
    }

    fun startStopWorking() {
        doAsync {
            try {
                if (isWorking) {
                    endWork.endWork()
                } else {
                    startWork.startWork()
                }
                isWorking = !isWorking
                uiThread { 
                    view.showIsWorking(isWorking)
                }
            } catch (e: RuntimeException) {
                error("error ${e.message}")
            }
        }
    }
    
    fun updateCurrentWorkingTime() {
        doAsync {
            val milliseconds = getCurrentWorkTime.getWorkTime()
            val time = convertToHumanReadableHours(milliseconds)
            uiThread { 
                view.showWorkTime(time)
            }
        }
    }
    
    private fun convertToHumanReadableHours(milliseconds: Long): String {
        val seconds = (milliseconds / 1000) % 60
        val minutes = (milliseconds / (1000 * 60) % 60)
        val hours = (milliseconds / (1000 * 60 * 60) % 24)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}

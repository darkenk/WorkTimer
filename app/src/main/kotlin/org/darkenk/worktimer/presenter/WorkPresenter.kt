package org.darkenk.worktimer.presenter

import org.darkenk.worktimer.usecases.EndWork
import org.darkenk.worktimer.usecases.GetCurrentWorkStatus
import org.darkenk.worktimer.usecases.StartWork
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class WorkPresenter(private val endWork: EndWork,
                    private val getCurrentWorkStatus: GetCurrentWorkStatus,
                    private val startWork: StartWork,
                    private val view: IMainView) {
    
    private var isWorking: Boolean = false
    
    fun init() {
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
}

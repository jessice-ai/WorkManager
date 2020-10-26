package com.example.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class SunMyWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        //接收传递过来的数据
        val name : String? = inputData.getString(INPUT_DATA_KEY)
        println("Jessice：传递进来的值："+name)
        println("Jessice：任务执行开始 $name ")

        /**
         * 后台任务代码放置区
         */
        Thread.sleep(3000)
        println("Jessice：任务执行结束 $name ")
        //返回一个成功提示，并传递数据出去
        return Result.success(workDataOf(OUT_DATA_KEY to "$name output"))
    }
}
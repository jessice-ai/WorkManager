package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

const val INPUT_DATA_KEY = "input_data_key"
const val WORK_A_NAME = "Work A"
const val OUT_DATA_KEY = "out_data_key"
class MainActivity : AppCompatActivity() {
    /**
     * WorkManager  第一步
     * 获取一个WorkManager的引用
     */
    private val sunworkManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            /**
             * WorkManager  第四步
             * 创建约束条件，也就是请求需要满足什么条件的时候，才能加入队列
             * 1、setRequiredNetworkType() 网络类型
             * 2、setRequiresDeviceIdle() 设备是否在空闲状态
             * 3、setRequiresCharging() 是否在充电
             * 4、setRequiresBatteryNotLow() 低电量模式
             * 5、setRequiresStorageNotLow() 存储空间不是在低的状态下
             *
             * setRequiredNetworkType 参数介绍
             * 1、NetworkType.CONNECTED 连接上网络
             * 2、NetworkType.UNMETERED 不计流量的网络，也就是wifi
             * 3、NetworkType.METERED 计流量的网络，比如4g网络
             *
             */

            val sunConstraints : Constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build()  //网络状态为连接时，作为请求约束条件

            /**
             * WorkManager  第二步
             * 创建工作请求
             * 请求分为两种，一种是单次请求，二种是周期性请求
             */
            val sunworkRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<SunMyWork>()
                /**
                 * WorkManager  第五步
                 * 添加完自定义约束条件之后，也就是只有在网络连接的时候，执行队列，断网情况下，点击按钮，加入到队列中，只有在联网的时候，才会执行队列中的内容
                 */
                .setConstraints(sunConstraints) //设置上面我们创建的约束条件
                /**
                 * WorkManager  第六步
                 * 给Work传递数据
                 */
                .setInputData(workDataOf(INPUT_DATA_KEY to WORK_A_NAME)) //这里的 workDataOf 参数key value 用to 连接
                .build()  //单次请求

            /**
             * WorkManager  第三步
             * 把工作请求，添加到队列里面
             */
            sunworkManager.enqueue(sunworkRequest)
            /**
             * WorkManager  第七步
             * WorkManager 是一个liveData ，所以可以观察 观察传递回来的值
             */
            sunworkManager.getWorkInfoByIdLiveData(sunworkRequest.id).observe(this, Observer {
                //这里需要判断下状态
                if(it.state == WorkInfo.State.SUCCEEDED){
                    println("Jessice：传递出来的值："+it.outputData.getString(OUT_DATA_KEY))
                }
            })
        }

    }
}
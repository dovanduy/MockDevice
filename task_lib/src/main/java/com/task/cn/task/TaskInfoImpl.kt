package com.task.cn.task

import com.google.gson.Gson
import com.safframework.log.L
import com.task.cn.Result
import com.task.cn.StatusCode
import com.task.cn.StatusMsg
import com.task.cn.jbean.AccountInfoBean
import com.task.cn.jbean.DeviceInfoBean
import com.task.cn.jbean.IpInfoBean
import com.task.cn.jbean.TaskBean
import com.utils.common.Utils
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Description:
 * Created by Quinin on 2020-03-03.
 **/
class TaskInfoImpl(private val taskInfoView: TaskInfoView) : ITaskInfo {

    override fun getTaskInfo() {
        Result(StatusCode.FAILED, TaskBean(), StatusMsg.DEFAULT.msg).run {
            try {
                val inputStream = Utils.getApp().assets.open("task_info.json")
                val bufferReader = BufferedReader(InputStreamReader(inputStream))
                val taskBean = Gson().fromJson(bufferReader, TaskBean::class.java)

                this.code = StatusCode.SUCCEED
                this.r = taskBean
                this.msg = StatusMsg.SUCCEED.msg
            } catch (e: Exception) {
                L.d(e.message)
                this.msg = if (e.message == null) "getTaskInfo json解析失败" else e.message!!
            }

            taskInfoView.onResponTaskInfo(this)
        }
    }

    override fun getAccountInfo() {
        Result(StatusCode.FAILED, AccountInfoBean(), StatusMsg.DEFAULT.msg).run {


            taskInfoView.onResponAccountInfo(this)
        }
    }

    override fun getIpInfo() {
        Result(StatusCode.FAILED, IpInfoBean(), StatusMsg.DEFAULT.msg).run {


            taskInfoView.onResponIpInfo(this)
        }
    }


    override fun getDeviceInfo() {
        Result(StatusCode.FAILED, DeviceInfoBean(), StatusMsg.DEFAULT.msg).run {


            taskInfoView.onResponDeviceInfo(this)
        }
    }

    override fun getLocationByIP(ip: String) {
        L.d("current ip: $ip")

    }

    override fun changeDeviceInfo(taskBean: TaskBean) {
        Result(StatusCode.FAILED, false, StatusMsg.DEFAULT.msg).run {
            val deviceInfoBean = taskBean.device_info

            taskInfoView.onChangeDeviceInfo(this)
        }
    }

}
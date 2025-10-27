package com.example.cheermum

import android.util.Log
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import org.json.JSONObject
import java.io.File
import kotlin.concurrent.thread

fun sendData(settingsFile: File, ipSettings: JSONObject): String{
    var result = ""
    thread {
        try {
            val jsch = JSch()
            val session = jsch.getSession("admin", ipSettings.get("ip").toString(), 22)//10.0.2.2 is localhost from android-studio's point of view
            session.setPassword("admin")                                //192.168.0.103 for matt's home network
            session.setConfig("StrictHostKeyChecking", "no")
            session.connect()

            if (session.isConnected) {
                Log.i("Connection", "Is connected!")
            }
            else{
                result = "Failed to connect to your bear!"
            }

            val channel = session.openChannel("sftp") as ChannelSftp
            channel.connect()
            channel.put(settingsFile.toString(), "/home/admin/Desktop/eng40011/settings.json")

            channel.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
            result = "Something went wrong"

        }
    }
    if (result == ""){
        result = "Successfully sent settings!"
    }

    return result
}
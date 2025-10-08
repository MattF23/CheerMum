package com.example.cheermum

import android.content.Context
import android.util.Log

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.io.File

fun sendData(applicationContext: Context, message: String, file: File): String {
    val jsch = JSch()
    val session = jsch.getSession("admin", "PUT PI IP HERE", 22)
    session.setPassword("admin")
    session.setConfig("StrictHostKeyChecking", "no")
    session.connect()

    if(session.isConnected){
        Log.i("Connection", "Is connected!")
    }

    val command = "scp -p -t " + file

    val channel = session.openChannel("exec")
    //Then need to set the command of the channel somehow
    //Not done but have to go!

    return "Successfully sent data"
}
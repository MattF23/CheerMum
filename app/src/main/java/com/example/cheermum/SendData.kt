package com.example.cheermum

import android.util.Log
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import kotlinx.coroutines.Runnable
import java.io.File
import kotlin.concurrent.thread

fun sendData(file: File): String {
    val output = StringBuilder()
    thread {
        try {
            val jsch = JSch()
            val session = jsch.getSession("matt", "localhost", 22)
            session.setPassword("Your password here")
            session.setConfig("StrictHostKeyChecking", "no")
            session.connect()

            if (session.isConnected) {
                Log.i("Connection", "Is connected!")
            }

            val command = "scp -p -t $file"

            val channel = session.openChannel("exec") as ChannelExec
            channel.setCommand(command)
            channel.connect()

            val inputStream = channel.inputStream
            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } > 0) {
                output.append(String(buffer, 0, bytesRead))
            }
            channel.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    var result = ""
    if (output.toString() == ""){
        result = "Something went wrong"
    }
    result = result + output.toString()//Returns output if it exists. Error message otherwise
    return result
}

/*class DataSender(): Runnable{
    override fun run(){
        try {
        val jsch = JSch()
        val session = jsch.getSession("matt", "localhost", 22)
        session.setPassword("mckinnon24680")
        session.setConfig("StrictHostKeyChecking", "no")
        session.connect()

        if (session.isConnected) {
            Log.i("Connection", "Is connected!")
        }

        val command = "scp -p -t $file"

        val channel = session.openChannel("exec") as ChannelExec
        channel.setCommand(command)
        channel.connect()

        val inputStream = channel.inputStream
        val buffer = ByteArray(1024)
        var bytesRead: Int
        val output = StringBuilder()

        while (inputStream.read(buffer).also { bytesRead = it } > 0) {
            output.append(String(buffer, 0, bytesRead))
        }
        channel.disconnect()


        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}*/
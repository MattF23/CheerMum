package com.example.cheermum

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

fun sendData(applicationContext: Context, message: String){
    val client = MqttAndroidClient ( applicationContext,"YOUR MQTT BROKER ADDRESS","YOUR CLIENT ID" )
    val topic = "settings/#"

    connect(/*applicationContext, */client)
    subscribe(topic, client)
    publish(topic, message, client)
    disconnect(client)
}

fun connect(/*applicationContext: Context, */client: MqttAndroidClient){
        try {
            val token = client.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken)                        {
               Log.i("Connection", "success ")
               //connectionStatus = true
               // Give your callback on connection established here
 }override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    //connectionStatus = false
                    Log.i("Connection", "failure")
                 // Give your callback on connection failure here
                    exception.printStackTrace()
                }
            }
        } catch (e: MqttException) {
            // Give your callback on connection failure here
            e.printStackTrace()
        }
}

fun disconnect(client: MqttAndroidClient) {
        try {
            val disconnectToken = client.disconnect()
            disconnectToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    //connectionStatus = false
                    // Give Callback on disconnection here
                }override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    Log.e("Disconnect", "Failed to disconnect from broker.")
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }}

fun subscribe(topic: String, client: MqttAndroidClient) {
    val qos = 2 // Mention your qos value
    try {
        client.subscribe(topic, qos, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                // Give your callback on Subscription here
            }override fun onFailure(
                asyncActionToken: IMqttToken,
                exception: Throwable
            ) {
                // Give your subscription failure callback here
                Log.e("Subscribe", "Failed to subscribe to topic")
            }
        })
    } catch (e: MqttException) {
        e.printStackTrace()
    }}

fun publish(topic: String, data: String, client: MqttAndroidClient) {
        val encodedPayload : ByteArray
        try {
            encodedPayload = data.toByteArray(charset("UTF-8"))
            val message = MqttMessage(encodedPayload)
            message.qos = 2
            message.isRetained = false
            client.publish(topic, message)
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: MqttException) {
            e.printStackTrace()
        }}
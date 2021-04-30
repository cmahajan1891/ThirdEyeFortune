package com.third.eye.thirdeyefortune.utils

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.third.eye.thirdeyefortune.log.Logger

class FFMpegService : Service() {
    private val mBinder: Binder = LocalBinder()
    private var fFMpeg: FFmpeg? = null
    var commands: Array<String>? = null
    private var callbacks: Callbacks? = null
    val percentage: MutableLiveData<Int> = MutableLiveData()

    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onCreate() {
        super.onCreate()
        initializeMpeg()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val duration = Integer.parseInt(intent.extras?.get("duration").toString())
            commands = intent.extras?.getStringArray("command") ?: arrayOf()
            initializeMpeg()
            executeCmd()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initializeMpeg() {
        fFMpeg = fFMpeg ?: FFmpeg.getInstance(this).also {
            try {
                it.loadBinary(object : LoadBinaryResponseHandler() {
                    override fun onSuccess() {

                    }

                    override fun onFailure() {
                        super.onFailure()
                    }
                })
            } catch (e: FFmpegNotSupportedException) {
                // Handle if FFmpeg is not supported by device
            }
        }
    }

    private fun executeCmd() {
        try {

            if (commands?.isNotEmpty() == true) {
                fFMpeg?.execute(commands, object : ExecuteBinaryResponseHandler() {
                    override fun onSuccess(s: String) {
                        Logger.d("FFMpegService", "Video successfully trimmed.")
                    }

                    override fun onFailure(s: String) {
                        Logger.d("FFMpegService", "Video trim unsuccessful.")
                    }

                    override fun onProgress(message: String?) {

                    }

                    override fun onFinish() {
                        percentage.postValue(100)
                    }
                })
            }
        } catch (e: FFmpegCommandAlreadyRunningException) {
            //There is a command already running
        }
    }

    fun registerClient(fragment: Fragment) {
        this.callbacks = fragment as Callbacks
    }

    interface Callbacks {
        fun updateClient(data: Float)
    }

    inner class LocalBinder : Binder() {
        fun getServiceInstance(): FFMpegService = this@FFMpegService
    }
}

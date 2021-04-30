package com.third.eye.thirdeyefortune.data.model

import android.net.Uri
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Video(
    @Expose
    @SerializedName("videoUri")
    val uri: Uri?
)

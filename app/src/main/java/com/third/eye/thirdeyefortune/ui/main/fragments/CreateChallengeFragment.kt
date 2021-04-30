package com.third.eye.thirdeyefortune.ui.main.fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.navigation.fragment.findNavController
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.data.model.Video
import com.third.eye.thirdeyefortune.databinding.CreateChallengeFragmentBinding
import com.third.eye.thirdeyefortune.di.components.FragmentComponent
import com.third.eye.thirdeyefortune.ui.base.BaseFragment
import com.third.eye.thirdeyefortune.ui.main.activities.ThirdEyeFortuneActivity
import com.third.eye.thirdeyefortune.ui.main.viewModels.CreateChallengeFragmentViewModel
import com.third.eye.thirdeyefortune.utils.FFMpegService
import java.io.File

const val REQUEST_VIDEO_CAPTURE = 1

class CreateChallengeFragment :
    BaseFragment<CreateChallengeFragmentViewModel, CreateChallengeFragmentBinding>() {

    private lateinit var videoView: VideoView
    private var fullDuration: Int = 0
    private lateinit var ffMpegService: FFMpegService
    private lateinit var serviceConnection: ServiceConnection
    private lateinit var command: Array<String>
    private lateinit var dest: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        dispatchTakeVideoIntent()
        return view
    }

    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == REQUEST_VIDEO_CAPTURE) {
            when (resultCode) {
                RESULT_CANCELED -> {
                    findNavController().navigate(CreateChallengeFragmentDirections.actionCreateChallengeFragmentToFeedsFragment())
                }
                RESULT_OK -> {
                    val videoUri = intent?.data

//                    trimVideo(0, 4000, "New", videoUri)
//
//                    val serviceIntent = Intent(requireContext(), FFMpegService::class.java).also {
//                        it.putExtra("duration", ((4000 - 0) / 1000).toString())
//                        it.putExtra("command", command)
//                        it.putExtra("destination", dest.absolutePath)
//                        requireActivity().startService(it)
//                    }

//                    serviceConnection = object : ServiceConnection {
//                        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder) {
//                            iBinder as FFMpegService.LocalBinder
//                            ffMpegService = iBinder.getServiceInstance()
//                            ffMpegService.registerClient(this@CreateChallengeFragment)
//
//
//                            ffMpegService.percentage.observe(
//                                this@CreateChallengeFragment,
//                                Observer {
//                                    if (it == 100) {
//                                        requireActivity().stopService(serviceIntent)
//                                        (requireActivity() as ThirdEyeFortuneActivity).viewModel.addVideosData(
//                                            Video(videoUri)
//                                        )
//                                        findNavController().navigate(
//                                            CreateChallengeFragmentDirections.actionCreateChallengeFragmentToFeedsFragment()
//                                        )
//                                    }
//                                })
//                        }
//
//                        override fun onServiceDisconnected(p0: ComponentName?) {
//
//                        }
//
//                    }
//                    requireActivity().bindService(
//                        serviceIntent,
//                        serviceConnection,
//                        BIND_AUTO_CREATE
//                    )
                    (requireActivity() as ThirdEyeFortuneActivity).viewModel.addVideosData(
                        Video(videoUri)
                    )
                    findNavController().navigate(
                        CreateChallengeFragmentDirections.actionCreateChallengeFragmentToFeedsFragment()
                    )
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent)
    }

//    private fun trimVideo(startMs: Int, endMs: Int, fileName: String, uri: Uri?) {
//        val folder = File("${Environment.getExternalStorageDirectory()}/TrimVideos")
//        if (!folder.exists()) {
//            folder.mkdir()
//        }
//        val fileExt = ".mp4"
//        dest = File(folder, "$fileName$fileExt")
//        val origPath = getPathFromUri(requireActivity().applicationContext, uri)
//
//        command = arrayOf(
//            "--ss",
//            "${startMs / 1000}",
//            "-y",
//            "-i",
//            origPath,
//            "-t",
//            "${(endMs - startMs) / 1000}",
//            "vcodec",
//            "mpeg4",
//            "-b:v",
//            "2097152",
//            "-b:a",
//            "48000",
//            "-ac",
//            "2",
//            "-ar",
//            "22050",
//            dest.absolutePath
//        )
//    }

//    private fun getPathFromUri(context: Context?, uri: Uri?): String {
//        var cursor: Cursor? = null
//        var path = ""
//        try {
//            uri?.let {
//                cursor = context?.contentResolver?.query(
//                    uri,
//                    arrayOf(MediaStore.Images.Media.DATA),
//                    null,
//                    null,
//                    null
//                )
//                val colIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                cursor?.moveToFirst()
//                path = colIndex?.let { cursor?.getString(colIndex) } ?: ""
//            }
//        } catch (exc: Exception) {
//            Logger.e(TAG, exc.message.toString())
//        } finally {
//            cursor?.close()
//            return path
//        }
//    }

    companion object {
        const val TAG = "CreateChallengeFragment"
        fun newInstance() = CreateChallengeFragment()
    }

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        videoView = view.findViewById(R.id.video_view)
//        videoView.setOnPreparedListener {
//            fullDuration = it.duration / 1000
//        }
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getLayoutResourceId(): Int = R.layout.create_challenge_fragment
//    override fun updateClient(data: Float) {
//
//    }

}

package com.mobile.hackatoners.maplevel.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R
import com.mobile.hackatoners.utils.DataHolder
import kotlinx.android.synthetic.main.activity_finish.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class WorldFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_finish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        total_coins.text = DataHolder.getInstance(requireContext()).coins.toString() + "р"

        finish_score_share.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                //Permission was denied
                //Request for permission
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 301
                )
            }

            shareScreenShootResult()
        }

        finish_close.setOnClickListener { activity?.finish() }
        finish_btn_main.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=ru.vtb.invest")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=ru.vtb.invest")
                    )
                )
            }
        }
    }

    val requestStoragePermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            var saveImageFlag = true
            permissions.entries.forEach {
                saveImageFlag = it.value
            }
            if (saveImageFlag) {
                shareScreenShootResult()
            }
        }

    val permissionListener: () -> Boolean = {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            requestStoragePermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
            false
        }
    }


    private fun shareScreenShootResult() {
        val dateFormatter by lazy {
            SimpleDateFormat(
                "yyyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault()
            )
        }
        val filename = "${"aloha"}${dateFormatter.format(Date())}.png"
        val ScreenShootFolderPath = File.separator + activity?.getAppName()

        val uri = finish_content.makeScreenShot()
            .saveScreenShot(filename, ScreenShootFolderPath, permissionListener)
            ?: return

        dispatchShareImageIntent(uri)
    }

    private fun dispatchShareImageIntent(screenShotUri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, screenShotUri)
        startActivity(Intent.createChooser(intent, "Share"))
    }

    fun Context.getAppName(): String {
        var appName: String = ""
        val applicationInfo = applicationInfo
        val stringId = applicationInfo.labelRes
        appName = if (stringId == 0) {
            applicationInfo.nonLocalizedLabel.toString()
        } else {
            getString(stringId)
        }
        return appName
    }

    fun View.makeScreenShot(): Bitmap {
        setBackgroundColor(Color.WHITE)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

    fun Bitmap.saveScreenShot(
        filename: String,
        ScreenShootFolderPath: String,
        permissionListener: () -> Boolean,
    ): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            saveImageInQ(this, filename, ScreenShootFolderPath, requireActivity().contentResolver)
        else
            legacySave(this, filename, ScreenShootFolderPath, permissionListener)
    }

    private fun legacySave(
        bitmap: Bitmap,
        filename: String,
        parentFileName: String,
        permissionListener: () -> Boolean,
    ): Uri? {
        val fos: OutputStream?
        if (!permissionListener()) {
            return null
        }

        val path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() +
                    parentFileName + File.separator + filename
        val imageFile = File(path)
        if (imageFile.parentFile?.exists() == false) {
            imageFile.parentFile?.mkdir()
        }
        imageFile.createNewFile()
        fos = FileOutputStream(imageFile)
        val uri: Uri = Uri.fromFile(imageFile)

        fos.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        fos.flush()
        fos.close()

        return uri
    }

    private fun saveImageInQ(
        bitmap: Bitmap,
        filename: String,
        parentFileName: String,
        contentResolver: ContentResolver
    ): Uri? {
        val fos: OutputStream?
        val uri: Uri?
        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.Files.FileColumns.MIME_TYPE, "image/png")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + parentFileName
            )
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let { contentResolver.openOutputStream(it) }.also { fos = it }

        fos?.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        fos?.flush()
        fos?.close()

        contentValues.clear()
        contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
        uri?.let {
            contentResolver.update(it, contentValues, null, null)
        }
        return uri
    }

}

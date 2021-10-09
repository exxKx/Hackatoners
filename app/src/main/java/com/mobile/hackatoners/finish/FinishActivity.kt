package com.mobile.hackatoners.finish

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.hackatoners.R
import kotlinx.android.synthetic.main.activity_finish.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class FinishActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view  =setContentView(R.layout.activity_finish)

        finish_score_share.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                //Permission was denied
                //Request for permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 301)
            }

            shareScreenShootResult()
        }

        finish_close.setOnClickListener { finish() }
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
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                applicationContext,
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
        val ScreenShootFolderPath = File.separator + this.getAppName()

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
            saveImageInQ(this, filename, ScreenShootFolderPath, contentResolver)
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
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() +
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
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + parentFileName)
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
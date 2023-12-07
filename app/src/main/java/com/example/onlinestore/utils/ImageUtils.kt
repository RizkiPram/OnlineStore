package com.example.onlinestore.utils

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import com.example.onlinestore.R
import okio.IOException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}

fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}

fun uriToFile(selectedImg: Uri, context: Context): ByteArray {
    var inStream: InputStream? = null
    try {
        inStream = context.contentResolver.openInputStream(selectedImg)!!
        var byteBuff = ByteArrayOutputStream()
        var bufferSize = 1024
        var buf = ByteArray(1024)
        var leng: Int = 0
        while (inStream.read(buf).also { leng = it } > 0) {
            byteBuff.write(buf, 0, leng)
        }
        return byteBuff.toByteArray()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException){
        e.printStackTrace()
    }

//    val contentResolver: ContentResolver = context.contentResolver
//    val myFile = createTempFile(context)
//
//    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
//    val outputStream: OutputStream = FileOutputStream(myFile)
//    val buf = ByteArray(1024)
//    var len: Int
//    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
//    outputStream.close()
//    inputStream.close()

    return ByteArray(1024)
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)

    var compressQuality = 100
    var streamLength: Int

    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)

    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

    return file
}

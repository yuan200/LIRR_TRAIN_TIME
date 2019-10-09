package com.yuan.nyctransit.extenstion

import android.content.Context
import com.google.transit.realtime.GtfsRealtime
import com.yuan.nyctransit.features.lirr.LirrFeed
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*


fun ResponseBody.writeResponseBodyToDisk( context: Context): Boolean {
    try {
        val filename = "lirrFeeder"
        val file = File(context.filesDir, filename)

        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            val fileReader = ByteArray(4096)

            val fileSize = this.contentLength()
            var fileSizeDownload = 0

            inputStream = this.byteStream()
            outputStream = FileOutputStream(file)

            while (true) {

                val feed: GtfsRealtime.FeedMessage = GtfsRealtime.FeedMessage.parseFrom(inputStream)
                LirrFeed.entityList = feed.entityList

                for (item in feed.entityList) {
                    if (item.hasTripUpdate()) {
                        Timber.d(item.tripUpdate.toString())
//                        Log.d("tripUpdate", item.tripUpdate.toString())
                    }
                }

                val read = inputStream.read(fileReader)
                if (read == -1) break

                outputStream.write(fileReader, 0, read)
                fileSizeDownload += read

                Timber.d("file downlaed: $fileSizeDownload of $fileSize")
            }
            outputStream.flush()
            return true

        } catch (e: IOException) {
            return false
        }  finally {
            inputStream ?: inputStream!!.close()
            outputStream ?: outputStream!!.close()
        }

    } catch (ex: IOException) {
        return false
    }
}
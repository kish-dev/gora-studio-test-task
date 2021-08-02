package kishdev.test.gorastudio.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object ImageDownloader {

    private const val INTERNET_CLIENT =
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
    private const val USER_AGENT = "User-Agent"

    suspend fun downloadImage(stringUrl: String): Bitmap? =
        withContext(Dispatchers.IO) {

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            val url = URL(stringUrl)
            val connection = url.openConnection()
            connection.setRequestProperty(
                USER_AGENT,
                INTERNET_CLIENT
            )
            val stream = connection.getInputStream()

            return@withContext BitmapFactory.decodeStream(stream)
        }
}

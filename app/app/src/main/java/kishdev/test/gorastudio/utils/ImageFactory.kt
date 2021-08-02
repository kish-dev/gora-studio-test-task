package kishdev.test.gorastudio.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import kishdev.test.gorastudio.R
import kishdev.test.gorastudio.data.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

object ImageFactory {

    private val images = ConcurrentHashMap<Int, Bitmap>()

    suspend fun loadImage(
        appCompatImageView: AppCompatImageView,
        progressBar: ProgressBar,
        photo: Photo,
        context: Context
    ) =
        withContext(Dispatchers.Main) {
            if (images.containsKey(photo.id)) {
                appCompatImageView.setImageBitmap(images[photo.id])
                progressBar.visibility = View.GONE
                appCompatImageView.visibility = View.VISIBLE
                return@withContext
            }

            if (ConnectiveManager.isConnectionExist(context)) {
                val image = ImageDownloader.downloadImage(photo.url)

                image?.let {
                    images[photo.id] = it
                }

                appCompatImageView.setImageBitmap(image)
            } else {
                appCompatImageView.setImageResource(R.drawable.ic_broken_image)
            }

            progressBar.visibility = View.GONE
            appCompatImageView.visibility = View.VISIBLE
        }
}

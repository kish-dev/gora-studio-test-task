package kishdev.test.gorastudio.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kishdev.test.gorastudio.R
import kishdev.test.gorastudio.data.Photo
import kishdev.test.gorastudio.utils.ImageFactory
import kotlinx.coroutines.*

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    private var photos: List<Photo> = emptyList()

    class PhotosViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_photo, parent, false) as CardView
        return PhotosViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val cardView = holder.cardView
        val appCompatImageView =
            cardView.findViewById<AppCompatImageView>(R.id.card_photo__user_photo_image_view)
        val appCompatTextView =
            cardView.findViewById<AppCompatTextView>(R.id.card_photo__photo_description_text_view)
        val progressBar = cardView.findViewById<ProgressBar>(R.id.card_photo__progress_bar)
        cardView.apply {
            CoroutineScope(Dispatchers.Main).launch {
                ImageFactory.loadImage(appCompatImageView, progressBar, photos[position], context)
            }
        }
        appCompatTextView.text = photos[position].title
    }

    override fun getItemCount(): Int =
        photos.size

    fun update(photos : List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}

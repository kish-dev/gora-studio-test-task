package kishdev.test.gorastudio.repositories

import kishdev.test.gorastudio.data.Photo

interface IPhotoRepository {
    fun getPhotosByUserId(id: Int): List<Photo>?
    fun savePhotos(id: Int, photos: List<Photo>)
}

object PhotosRepository : IPhotoRepository {

    private var photos = mutableMapOf<Int, List<Photo>>()

    override fun getPhotosByUserId(id: Int): List<Photo>? =
        photos[id]

    override fun savePhotos(id: Int, photos: List<Photo>) {
        this.photos[id] = photos
    }
}

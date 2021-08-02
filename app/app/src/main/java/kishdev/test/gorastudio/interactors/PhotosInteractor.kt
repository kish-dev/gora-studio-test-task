package kishdev.test.gorastudio.interactors

import kishdev.test.gorastudio.data.Photo
import kishdev.test.gorastudio.network.INetworkApiService
import kishdev.test.gorastudio.repositories.IPhotoRepository

class PhotosInteractor(
    private val networkApiService: INetworkApiService,
    private val photoRepository: IPhotoRepository
) {
    suspend fun getPhotos(id: Int): List<Photo> {
        return if (photoRepository.getPhotosByUserId(id) == null) {
            networkApiService.getPhotosByAlbumId(id)
        } else {
            val list = networkApiService.getPhotosByAlbumId(id)
            photoRepository.savePhotos(id, list)
            list
        }
    }
}

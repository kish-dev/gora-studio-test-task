package kishdev.test.gorastudio.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kishdev.test.gorastudio.data.DataScope
import kishdev.test.gorastudio.data.Photo
import kishdev.test.gorastudio.interactors.PhotosInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosViewModel(
    private val interactor: PhotosInteractor
) : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos


    fun updatePhotos(id: Int) {
        DataScope.launch {
            _status.postValue(Status.LOADING)
            try {
                withContext(Dispatchers.Main) {
                    val list = interactor.getPhotos(id)
                    _status.postValue(Status.SUCCESS)
                    _photos.postValue(list)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _status.postValue(Status.ERROR)
                    _photos.postValue(emptyList())
                }
            }
        }
    }
}

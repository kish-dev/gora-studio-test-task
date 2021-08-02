package kishdev.test.gorastudio.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kishdev.test.gorastudio.interactors.PhotosInteractor

class PhotosViewModelFactory(private val interactor: PhotosInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(PhotosInteractor::class.java).newInstance(interactor)
}

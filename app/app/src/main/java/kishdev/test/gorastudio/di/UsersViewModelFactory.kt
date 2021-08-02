package kishdev.test.gorastudio.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kishdev.test.gorastudio.interactors.UsersInteractor

class UsersViewModelFactory(private val interactor: UsersInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(UsersInteractor::class.java).newInstance(interactor)
}

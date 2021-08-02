package kishdev.test.gorastudio.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kishdev.test.gorastudio.data.DataScope
import kishdev.test.gorastudio.data.User
import kishdev.test.gorastudio.interactors.UsersInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel(private val interactor: UsersInteractor) : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users


    fun updateUsers() {
        DataScope.launch {
            _status.postValue(Status.LOADING)
            try {
                val list = interactor.getUsers()
                withContext(Dispatchers.Main) {
                    _status.postValue(Status.SUCCESS)
                    _users.postValue(list)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _status.postValue(Status.ERROR)
                    _users.postValue(emptyList())
                }
            }
        }
    }
}

package kishdev.test.gorastudio.interactors

import kishdev.test.gorastudio.data.User
import kishdev.test.gorastudio.network.INetworkApiService
import kishdev.test.gorastudio.repositories.IUserRepository

class UsersInteractor(
    private val networkApiService: INetworkApiService,
    private val usersRepository: IUserRepository
) {
    suspend fun getUsers(): List<User> {
        return if (usersRepository.getUsers().isEmpty()) {
            val list = networkApiService.getUsers()
            usersRepository.saveUsers(list)
            list
        } else {
            usersRepository.getUsers()
        }
    }
}

package kishdev.test.gorastudio.repositories

import kishdev.test.gorastudio.data.User

interface IUserRepository {
    fun getUsers(): List<User>
    fun saveUsers(users: List<User>)
}

object UsersRepository : IUserRepository {

    private var users = mutableListOf<User>()

    override fun getUsers(): List<User> =
        users

    override fun saveUsers(users: List<User>) {
        this.users.addAll(users)
    }
}

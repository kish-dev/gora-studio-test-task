package kishdev.test.gorastudio.network

import android.net.UrlQuerySanitizer
import kishdev.test.gorastudio.data.Photo
import kishdev.test.gorastudio.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.URL

interface INetworkApiService {
    suspend fun getUsers(): List<User>
    suspend fun getPhotosByAlbumId(id: Int): List<Photo>
}

object NetworkApiService : INetworkApiService {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    private suspend fun download(url: String): JSONArray =
        withContext(Dispatchers.IO) {
            val urlConnection = URL(url).openConnection()
            val stream = urlConnection.getInputStream()
            val reader = BufferedReader(InputStreamReader(stream))
            val result = reader.readText()
            return@withContext JSONArray(result)
        }

    override suspend fun getUsers(): List<User> {
        val jsonArray = download("$BASE_URL/users")
        val list = mutableListOf<User>()
        var i = 0
        while (jsonArray.length() != i) {
            val obj = JSONObject(jsonArray.get(i).toString())
            list.add(
                User(
                    obj.getInt("id"),
                    obj.getString(
                        "name"
                    )
                )
            )
            ++i
        }
        return list
    }

    override suspend fun getPhotosByAlbumId(id: Int): List<Photo> {
        val jsonArray = download("$BASE_URL/photos?albumId=$id")
        val list = mutableListOf<Photo>()
        var i = 0
        while (jsonArray.length() != i) {
            val obj = JSONObject(jsonArray.get(i).toString())
            list.add(
                Photo(
                    obj.getInt("albumId"),
                    obj.getInt("id"),
                    obj.getString("title"),
                    obj.getString("url"),
                )
            )
            ++i
        }
        return list
    }
}


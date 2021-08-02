package kishdev.test.gorastudio.utils

import android.content.Context
import android.net.ConnectivityManager

object ConnectiveManager {
    fun isConnectionExist(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

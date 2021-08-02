package kishdev.test.gorastudio.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

object DataScope : CoroutineScope {
    private val parentJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    val scope = CoroutineScope(coroutineContext)
    fun release() {
        coroutineContext.cancelChildren()
    }
}

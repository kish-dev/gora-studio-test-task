package kishdev.test.gorastudio.views.listeners

interface ToolbarManager {
    fun setTitle(title: String)
    fun setPreviousTitle(title: String)
    fun setVisibilities(buttonBack: Boolean, previousTitle: Boolean, title: Boolean)
    fun internetConnectionNotAvailable()
    fun internetConnectionAvailable()
}

package kishdev.test.gorastudio.views.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import kishdev.test.gorastudio.R
import kishdev.test.gorastudio.views.listeners.ToolbarManager
import kishdev.test.gorastudio.views.users.UsersFragment

class MainActivity : AppCompatActivity(), ToolbarManager {

    private lateinit var toolbar: Toolbar
    private lateinit var buttonBack: ImageButton
    private lateinit var title: AppCompatTextView
    private lateinit var previousTitle: AppCompatTextView
    private lateinit var noInternetTitle: AppCompatTextView
    private lateinit var reconnect: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        buttonBack = findViewById(R.id.main_activity__toolbar_back_image_button)
        buttonBack.setOnClickListener {
            onBackPressed()
        }

        title = findViewById(R.id.main_activity__toolbar_title_text_view)
        previousTitle = findViewById(R.id.main_activity_toolbar_previous_name_text_view)
        previousTitle.setOnClickListener {
            onBackPressed()
        }
        noInternetTitle = findViewById(R.id.main_activity__toolbar_no_internet_text_view)
        reconnect = findViewById(R.id.main_activity__toolbar_reconnect_text_view)
        reconnect.setOnClickListener {
            val fragmentManager = this.supportFragmentManager
            for (i in 0 until fragmentManager.backStackEntryCount) {
                fragmentManager.popBackStack()
            }
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity__fragment_container, UsersFragment())
                .commit()
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.main_activity__fragment_container, UsersFragment())
            .commit()
    }

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setPreviousTitle(title: String) {
        this.previousTitle.text = title
    }

    override fun setVisibilities(buttonBack: Boolean, previousTitle: Boolean, title: Boolean) {
        when (buttonBack) {
            true -> {
                this.buttonBack.visibility = View.VISIBLE
            }
            false -> {
                this.buttonBack.visibility = View.GONE
            }
        }
        when (previousTitle) {
            true -> {
                this.previousTitle.visibility = View.VISIBLE
            }
            false -> {
                this.previousTitle.visibility = View.GONE
            }
        }
        when (title) {
            true -> {
                this.title.visibility = View.VISIBLE
            }
            false -> {
                this.title.visibility = View.GONE
            }
        }
    }

    override fun internetConnectionAvailable() {
        this.toolbar.setBackgroundColor(Color.parseColor("#10000000"))
        this.noInternetTitle.visibility = View.GONE
        this.reconnect.visibility = View.GONE
    }

    override fun internetConnectionNotAvailable() {
        this.buttonBack.visibility = View.GONE
        this.previousTitle.visibility = View.GONE
        this.title.visibility = View.GONE
        this.toolbar.setBackgroundColor(Color.parseColor("#D30713"))
        this.noInternetTitle.visibility = View.VISIBLE
        this.reconnect.visibility = View.VISIBLE
    }
}

package kishdev.test.gorastudio.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kishdev.test.gorastudio.R
import kishdev.test.gorastudio.data.User
import kishdev.test.gorastudio.views.listeners.IClickNameListener

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    var users: List<User> = emptyList()
    private lateinit var iClickNameListener: IClickNameListener

    inner class UserViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_user, parent, false) as CardView
        return UserViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val cardView = holder.cardView
        cardView.setOnClickListener {
            iClickNameListener.onClickName(users[position].userId)
        }

        val textView = cardView.findViewById<TextView>(R.id.card_user__name_text_view)
        textView.text = users[position].userName
    }

    override fun getItemCount(): Int =
        users.size

    fun initListener(iClickNameListener: IClickNameListener) {
        this.iClickNameListener = iClickNameListener
    }

    fun update(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
}

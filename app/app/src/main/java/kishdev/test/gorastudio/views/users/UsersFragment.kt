package kishdev.test.gorastudio.views.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kishdev.test.gorastudio.R
import kishdev.test.gorastudio.adapters.UsersAdapter
import kishdev.test.gorastudio.di.UsersViewModelFactory
import kishdev.test.gorastudio.interactors.UsersInteractor
import kishdev.test.gorastudio.network.NetworkApiService
import kishdev.test.gorastudio.repositories.UsersRepository
import kishdev.test.gorastudio.utils.ConnectiveManager
import kishdev.test.gorastudio.viewmodels.Status
import kishdev.test.gorastudio.viewmodels.UsersViewModel
import kishdev.test.gorastudio.views.photos.PhotosFragment
import kishdev.test.gorastudio.views.listeners.IClickNameListener
import kishdev.test.gorastudio.views.listeners.ToolbarManager

class UsersFragment : Fragment() {

    private val usersViewModelFactory =
        UsersViewModelFactory(UsersInteractor(NetworkApiService, UsersRepository))

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var iClickNameListener: IClickNameListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        recyclerView = view.findViewById(R.id.fragment_user__recycler_view)
        progressBar = view.findViewById(R.id.fragment_user__progress_bar)

        initViewModel()
        initListener()
        initObservers()

        usersAdapter = UsersAdapter()
        usersAdapter.initListener(iClickNameListener)
        recyclerView.adapter = usersAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val toolbar = activity as ToolbarManager

        if (ConnectiveManager.isConnectionExist(requireContext())) {
            toolbar.internetConnectionAvailable()
            toolbar.setPreviousTitle("")
            toolbar.setTitle("Users")
            toolbar.setVisibilities(buttonBack = false, previousTitle = false, title = true)
        } else {
            toolbar.internetConnectionNotAvailable()
        }

        usersViewModel.updateUsers()

        return view
    }

    private fun initObservers() {
        usersViewModel.status.observe(viewLifecycleOwner) {
            when (it!!) {
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }

        usersViewModel.users.observe(viewLifecycleOwner) {
            usersAdapter.update(it)
        }

    }

    private fun initViewModel() {
        usersViewModel = usersViewModelFactory.create(UsersViewModel::class.java)
    }

    private fun initListener() {
        iClickNameListener = object : IClickNameListener {
            override fun onClickName(id: Int) {
                fragmentManager!!.beginTransaction()
                    .replace(
                        R.id.main_activity__fragment_container,
                        PhotosFragment.newInstance(id),
                        null
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}

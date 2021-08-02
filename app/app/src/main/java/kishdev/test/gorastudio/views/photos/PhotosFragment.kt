package kishdev.test.gorastudio.views.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kishdev.test.gorastudio.R
import kishdev.test.gorastudio.adapters.PhotosAdapter
import kishdev.test.gorastudio.di.PhotosViewModelFactory
import kishdev.test.gorastudio.interactors.PhotosInteractor
import kishdev.test.gorastudio.network.NetworkApiService
import kishdev.test.gorastudio.repositories.PhotosRepository
import kishdev.test.gorastudio.utils.ConnectiveManager
import kishdev.test.gorastudio.viewmodels.PhotosViewModel
import kishdev.test.gorastudio.viewmodels.Status
import kishdev.test.gorastudio.views.listeners.ToolbarManager

class PhotosFragment : Fragment() {

    private val photosViewModelFactory =
        PhotosViewModelFactory(PhotosInteractor(NetworkApiService, PhotosRepository))

    private var id: Int? = null

    private lateinit var recyclerVeiw: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var photosViewModel: PhotosViewModel
    private lateinit var photosAdapter: PhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)

        recyclerVeiw = view.findViewById(R.id.fragment_photo__recycler_view)
        progressBar = view.findViewById(R.id.fragment_photo__progress_bar)
        progressBar.visibility = View.VISIBLE
        initViewModel()
        initObservers()

        photosAdapter = PhotosAdapter()
        recyclerVeiw.adapter = photosAdapter
        recyclerVeiw.layoutManager = LinearLayoutManager(context)
        id = arguments?.getInt(EXTRA_ID)!!

        val toolbar = activity as ToolbarManager
        if (ConnectiveManager.isConnectionExist(requireContext())) {
            toolbar.setPreviousTitle("Users")
            toolbar.setTitle("Photos")
            toolbar.setVisibilities(buttonBack = true, previousTitle = true, title = true)
        } else {
            toolbar.internetConnectionNotAvailable()
        }

        photosViewModel.updatePhotos(id!!)

        return view
    }

    private fun initViewModel() {
        photosViewModel = photosViewModelFactory.create(PhotosViewModel::class.java)
    }

    private fun initObservers() {
        photosViewModel.status.observe(viewLifecycleOwner) {
            when (it!!) {
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerVeiw.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    recyclerVeiw.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    recyclerVeiw.visibility = View.VISIBLE
                }
            }
        }

        photosViewModel.photos.observe(viewLifecycleOwner) {
            photosAdapter.update(it)
        }
    }

    companion object {

        const val EXTRA_ID = "id"

        fun newInstance(id: Int): Fragment {
            val args = Bundle()
            args.putInt(EXTRA_ID, id)
            val fragment = PhotosFragment()
            fragment.arguments = args
            return fragment
        }
    }

}

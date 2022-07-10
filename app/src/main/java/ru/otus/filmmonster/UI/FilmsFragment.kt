package ru.otus.filmmonster.UI

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.otus.filmmonster.*
import ru.otus.filmmonster.databinding.FragmentFilmBinding
import ru.otus.filmmonster.lib.CheckableImageView
import ru.otus.filmmonster.repository.FilmModel
import ru.otus.filmmonster.repository.FilmsRepository

open class FilmsFragment : Fragment() {

    lateinit var viewModel: FilmsViewModel
    private lateinit var binding : FragmentFilmBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var loadStateHolder: LoadAdapter.Holder
    private lateinit var filmAdapter: FilmItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), FilmsViewModelFactory(FilmsRepository()))[FilmsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmBinding.inflate( inflater,container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()

        viewModel.error.observe(viewLifecycleOwner, Observer<String> { error ->
            Snackbar.make(
                binding.root,
                error,
               5000
            )
                .setAction(R.string.try_again) {
                    filmAdapter.refresh()
                }
                .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                .show()
        } )
    }

    open fun initRecycler(){

        recyclerView = binding.recycler
        val horizontalItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
            ?.let { horizontalItemDecoration.setDrawable(it) }
        recyclerView.addItemDecoration(horizontalItemDecoration)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val verticalItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
            ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
                ?.let { verticalItemDecoration.setDrawable(it) }
            recyclerView.addItemDecoration(verticalItemDecoration)
        }

         filmAdapter = FilmItemAdapter(
            {id -> onFilmDetailsClick(id)},
            {id, likeView -> onLikeClick(id, likeView)},
             viewModel.highlightedFilmID
        )

        observePagedFilms(filmAdapter)

        val tryAgainAction: TryAgainAction = { filmAdapter.refresh()}
        val loadAdapter = LoadAdapter(tryAgainAction)
        val adapterWithLoadState = filmAdapter.withLoadStateFooter(loadAdapter)
        recyclerView.adapter = adapterWithLoadState

        loadStateHolder = LoadAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction
        )

        observeLoadState(filmAdapter)
    }

    open fun observePagedFilms(filmAdapter: FilmItemAdapter) {
        viewModel.pagedFilms.observe(viewLifecycleOwner, Observer<PagingData<FilmModel>> {
            lifecycleScope.launch { filmAdapter.submitData(it) }
        })
    }

    open fun onFilmDetailsClick(id: Int) {
        viewModel.onFilmClick(id)
        (activity as MainActivity).onFilmDetailsClick(id)
    }

    open fun onLikeClick(id: Int, likeView: CheckableImageView){
        viewModel.onLikeChanged(id)
        likeView.toggle()
        view?.let {
            Snackbar.make(
                it,
                if (likeView.isChecked) R.string.likeSnackbar
                else R.string.dislikeSnackbar, Snackbar.LENGTH_SHORT
            )
                .setAction(R.string.cancel) {
                    viewModel.onLikeChanged(id)
                    likeView.toggle()
                }
                .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                .show()
        }
    }

    private fun observeLoadState(adapter: FilmItemAdapter) {
        // you can also use adapter.addLoadStateListener
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                loadStateHolder.bind(state.refresh)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FilmsFragment()
    }
}


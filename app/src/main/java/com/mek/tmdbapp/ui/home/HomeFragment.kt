package com.mek.tmdbapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mek.tmdbapp.R
import com.mek.tmdbapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         viewModel.getMovieList()
        observeEvents()

    }

    private fun observeEvents() {
        viewModel.erorMessage.observe(viewLifecycleOwner){ eror ->
            binding.txtErorMessageInfo.text = eror
            binding.txtErorMessageInfo.isVisible = true
        }

        viewModel.isLoading.observe(viewLifecycleOwner){ loading ->
            binding.progressBar.isVisible = loading
        }
        viewModel.movieList.observe(viewLifecycleOwner){ list ->
            if (list.isNullOrEmpty()){
                binding.txtErorMessageInfo.text = "hiç film bulunamadı :("
                binding.txtErorMessageInfo.isVisible = true
            }else{
                movieAdapter = MovieAdapter(list, object  : MovieClickListener{
                    override fun onMovieClick(movieId: Int) {
                        movieId?.let {
                            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                            findNavController().navigate(action)
                        }
                    }

                })
                binding.homeRecyclerView.adapter = movieAdapter
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
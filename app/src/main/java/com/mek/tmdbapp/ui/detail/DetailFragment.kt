package com.mek.tmdbapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mek.tmdbapp.MainActivity
import com.mek.tmdbapp.Util.loadImage
import com.mek.tmdbapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private  var _binding : FragmentDetailBinding ?= null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieDetail(movieId = args.MovieId)
        observeEvents()

    }

    private fun observeEvents() {
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBarDetail.isVisible = it
        }
        viewModel.erorMessage.observe(viewLifecycleOwner){
            binding.txtDetailEror.text = it
            binding.txtDetailEror.isVisible = true
        }
        viewModel.movieResponse.observe(viewLifecycleOwner){ movie ->
            binding.imageViewMovieDetail.loadImage(movie.backdropPath)
            binding.txtDetailVote.text = movie.voteAverage.toString()
            binding.txtDetailBuilding.text = movie.productionCompanies?.first()?.name
            binding.txtDetailLanguage.text = movie.spokenLanguages?.first()?.englishName

            binding.txtDetailOverview.text = movie.overview
            binding.movieDetailGroup.isVisible = true

            (requireActivity() as MainActivity).supportActionBar?.title = movie.title
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
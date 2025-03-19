package com.mek.tmdbapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mek.tmdbapp.Util.Constants
import com.mek.tmdbapp.model.MovieDetailResponse
import com.mek.tmdbapp.network.ApiClient
import kotlinx.coroutines.launch

class DetailViewModel() : ViewModel() {
    val movieResponse : MutableLiveData<MovieDetailResponse> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val erorMessage : MutableLiveData<String?> = MutableLiveData()

    fun getMovieDetail(movieId : Int){
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getMovieDetail(movieId = movieId.toString(), token = Constants.BEARER_TOKEN)
                if (response.isSuccessful){
                    movieResponse.postValue(response.body())
                }else{
                    if (response.message().isNullOrEmpty()){
                        erorMessage.value = "bilinmeyen bir hata oluştu"
                    }else{
                        erorMessage.value = response.message()
                    }
                }
            }catch (e:Exception){
                erorMessage.value = e.message
            }finally {
                isLoading.value = false
            }
        }
    }
}
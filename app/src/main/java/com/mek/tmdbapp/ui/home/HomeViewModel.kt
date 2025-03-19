package com.mek.tmdbapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mek.tmdbapp.Util.Constants
import com.mek.tmdbapp.model.MovieItems
import com.mek.tmdbapp.network.ApiClient
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    val movieList : MutableLiveData<List<MovieItems?>?> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val erorMessage : MutableLiveData<String?> = MutableLiveData()

    fun getMovieList(){
        isLoading.value=true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getMovieList(token = Constants.BEARER_TOKEN)

                if (response.isSuccessful){
                    movieList.postValue(response.body()?.movieItems)
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
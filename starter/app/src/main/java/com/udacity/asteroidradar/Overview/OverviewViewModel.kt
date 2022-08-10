package com.udacity.asteroidradar.Overview

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.utils.Constants.API_KEY
import kotlinx.coroutines.launch
import org.json.JSONObject

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

@RequiresApi(Build.VERSION_CODES.N)
class OverviewViewModel : ViewModel() {


    private var _navigateToSelectedProperty = MutableLiveData<Asteroid>()
    val navigateToSelectedProperty: LiveData<Asteroid>
        get() = _navigateToSelectedProperty

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<Asteroid>()

    // The external immutable LiveData for the request status
    val status: LiveData<Asteroid>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<Asteroid>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<Asteroid>>
        get() = _properties

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    init {
        viewModelScope.launch {
            getAsteroidList()
            getPictureOfDay()
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getAsteroidList() {
        viewModelScope.launch {
            try {
                var asteroidListToParse = Api.retrofitService.getProperties(API_KEY)
                val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidListToParse))
                _properties.value = asteroidList
            } catch (e: Exception) {
            }
        }
    }

    private suspend fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfTheDay.postValue(
                    Api.retrofitService.getPictureOfTheDay(API_KEY)
                )
            } catch (e: Exception) {
                Log.e("getPictureOfDay", e.message.toString())
            }
        }
    }
}
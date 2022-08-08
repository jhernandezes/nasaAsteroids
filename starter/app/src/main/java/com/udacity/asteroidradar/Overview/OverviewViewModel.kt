package com.udacity.asteroidradar.Overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.domain.Asteroid

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

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

}
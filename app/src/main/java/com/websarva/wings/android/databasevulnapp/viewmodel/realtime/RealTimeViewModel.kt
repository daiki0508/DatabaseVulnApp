package com.websarva.wings.android.databasevulnapp.viewmodel.realtime

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.databasevulnapp.model.Database
import com.websarva.wings.android.databasevulnapp.repository.RealTimeRepositoryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealTimeViewModel @Inject constructor(
    private val realTimeRepository: RealTimeRepositoryClient,
    application: Application
) : AndroidViewModel(application) {
    private val _user = MutableLiveData<Database>()
    val user: LiveData<Database> = Transformations.map(_user){
        it
    }

    fun getData(){
        viewModelScope.launch {
            realTimeRepository.getData {
                if (it != null){
                    _user.value = it
                }
            }
        }
    }
}
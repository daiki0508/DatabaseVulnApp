package com.websarva.wings.android.databasevulnapp.viewmodel.internal

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.websarva.wings.android.databasevulnapp.model.Users
import com.websarva.wings.android.databasevulnapp.repository.FirebaseStorageRepositoryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InternalViewModel @Inject constructor(
    private val firebaseStorageRepository: FirebaseStorageRepositoryClient,
    application: Application
): AndroidViewModel(application) {
    private val _users = MutableLiveData<Users>()
    val user: LiveData<Users> = Transformations.map(_users){
        it
    }

    fun get(){
        viewModelScope.launch {
            firebaseStorageRepository.get(getApplication<Application>().applicationContext) {
                if (it != null){
                    _users.value = it
                }
            }
        }
    }
}
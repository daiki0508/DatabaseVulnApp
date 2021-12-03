package com.websarva.wings.android.databasevulnapp.viewmodel.external

import android.app.Application
import androidx.lifecycle.*
import com.websarva.wings.android.databasevulnapp.model.Users
import com.websarva.wings.android.databasevulnapp.repository.FirebaseStorageRepositoryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExternalViewModel @Inject constructor(
    private val firebaseStorageRepository: FirebaseStorageRepositoryClient,
    application: Application
) : AndroidViewModel(application) {
    private val _users = MutableLiveData<Users>()
    val user: LiveData<Users> = Transformations.map(_users){
        it
    }

    fun getEx(){
        viewModelScope.launch {
            firebaseStorageRepository.getEx(getApplication<Application>().applicationContext) {
                if (it != null){
                    _users.value = it
                }
            }
        }
    }
}
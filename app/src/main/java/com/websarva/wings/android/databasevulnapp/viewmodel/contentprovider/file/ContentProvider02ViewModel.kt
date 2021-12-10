package com.websarva.wings.android.databasevulnapp.viewmodel.contentprovider.file

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.android.databasevulnapp.repository.ContentProviderRepositoryClient
import com.websarva.wings.android.databasevulnapp.repository.FirebaseStorageRepositoryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentProvider02ViewModel @Inject constructor(
    private val firebaseStorageRepository: FirebaseStorageRepositoryClient,
    private val contentProviderRepository: ContentProviderRepositoryClient,
    application: Application
) : AndroidViewModel(application) {
    fun openFile(){
        viewModelScope.launch {
            firebaseStorageRepository.getUrlFile(getApplication<Application>().applicationContext) {
                if (it){
                    viewModelScope.launch {
                        // Log.i("openFile","loadUrl: ${contentProviderRepository.openFile(getApplication<Application>().applicationContext)}")
                        contentProviderRepository.openFile(getApplication<Application>().applicationContext)
                    }
                }
            }
        }
    }
}
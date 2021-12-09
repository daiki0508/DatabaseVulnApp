package com.websarva.wings.android.databasevulnapp.viewmodel.contentprovider.file

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.android.databasevulnapp.repository.ContentProviderRepositoryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentProvider02ViewModel @Inject constructor(
    private val contentProviderRepository: ContentProviderRepositoryClient,
    application: Application
) : AndroidViewModel(application) {
    fun getAssetsFile(){
        viewModelScope.launch {
            // Log.i("getAssetsFile","loadUrl: ${contentProviderRepository.getAssetsFile(getApplication<Application>().applicationContext)}")
            contentProviderRepository.getAssetsFile(getApplication<Application>().applicationContext)
        }
    }
}
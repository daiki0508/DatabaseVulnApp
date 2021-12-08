package com.websarva.wings.android.databasevulnapp.viewmodel.contentprovider.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.android.databasevulnapp.repository.ContentProviderRepositoryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ContentProvider01ViewModel @Inject constructor(
    private val contentProviderRepository: ContentProviderRepositoryClient,
    application: Application
) : AndroidViewModel(application) {
    fun insert(){
        if (!File("${getApplication<Application>().applicationContext.filesDir}../databases/content_provider01.db").exists()){
            viewModelScope.launch {
                contentProviderRepository.insert(getApplication<Application>().applicationContext)
            }
        }else{
            Log.i("ContentProvider01ViewModel", "file exists.")
        }
    }
}
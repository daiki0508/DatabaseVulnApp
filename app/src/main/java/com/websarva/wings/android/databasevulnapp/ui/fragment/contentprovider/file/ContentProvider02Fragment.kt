package com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.file

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.websarva.wings.android.databasevulnapp.R
import com.websarva.wings.android.databasevulnapp.databinding.FragmentContentprovider02Binding
import com.websarva.wings.android.databasevulnapp.ui.MainActivity
import com.websarva.wings.android.databasevulnapp.viewmodel.contentprovider.file.ContentProvider02ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentProvider02Fragment: Fragment() {
    private var _binding: FragmentContentprovider02Binding? = null
    private val binding
    get() = _binding!!

    private val viewModel: ContentProvider02ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentprovider02Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // backボタンタップ時の処理
        requireActivity().onBackPressedDispatcher.addCallback(this){
            activity?.let {
                with(Intent(it, MainActivity::class.java)){
                    it.finish()
                    startActivity(this)
                    it.overridePendingTransition(R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
                }
            }
        }

        // load_url内のurlを取得
        viewModel.openFile()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
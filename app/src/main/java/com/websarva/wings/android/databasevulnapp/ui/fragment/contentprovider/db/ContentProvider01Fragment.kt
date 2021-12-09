package com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.db

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.websarva.wings.android.databasevulnapp.R
import com.websarva.wings.android.databasevulnapp.databinding.FragmentContentprovider01Binding
import com.websarva.wings.android.databasevulnapp.ui.MainActivity
import com.websarva.wings.android.databasevulnapp.viewmodel.contentprovider.db.ContentProvider01ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentProvider01Fragment: Fragment() {
    private var _binding:FragmentContentprovider01Binding? = null
    private val binding
    get() = _binding!!

    private val viewModel: ContentProvider01ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentprovider01Binding.inflate(inflater, container, false)

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

        // db作成
        viewModel.insert()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
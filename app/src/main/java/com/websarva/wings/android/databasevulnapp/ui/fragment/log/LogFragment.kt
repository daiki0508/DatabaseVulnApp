package com.websarva.wings.android.databasevulnapp.ui.fragment.log

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.websarva.wings.android.databasevulnapp.R
import com.websarva.wings.android.databasevulnapp.databinding.FragmentLogBinding
import com.websarva.wings.android.databasevulnapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogFragment: Fragment() {
    private var _binding: FragmentLogBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogBinding.inflate(inflater, container, false)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
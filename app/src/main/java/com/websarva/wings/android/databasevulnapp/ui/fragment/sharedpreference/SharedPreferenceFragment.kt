package com.websarva.wings.android.databasevulnapp.ui.fragment.sharedpreference

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.websarva.wings.android.databasevulnapp.R
import com.websarva.wings.android.databasevulnapp.databinding.FragmentSharedpreferenceBinding
import com.websarva.wings.android.databasevulnapp.ui.MainActivity
import com.websarva.wings.android.databasevulnapp.ui.fragment.AlertDialogFragment
import com.websarva.wings.android.databasevulnapp.viewmodel.sharedpreference.SharedPreferenceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedPreferenceFragment: Fragment() {
    private var _binding: FragmentSharedpreferenceBinding? = null
    private val binding
    get() = _binding!!

    private val viewModel: SharedPreferenceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSharedpreferenceBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // backButtonがタップされた時の処理
        requireActivity().onBackPressedDispatcher.addCallback(this){
            activity?.let {
                with(Intent(it, MainActivity::class.java)){
                    it.finish()
                    startActivity(this)
                    it.overridePendingTransition(R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
                }
            }
        }

        // sharedPreferenceの作成
        viewModel.createPreference()

        // buttonタップ時の処理
        binding.button.setOnClickListener {
            viewModel.getPassword()
        }

        // passwordの通知
        viewModel.password.observe(this.viewLifecycleOwner, { value ->
            activity?.let {
                AlertDialogFragment(binding.edText.text.toString() == value.password).show(it.supportFragmentManager, "SharedPreferenceDialog")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
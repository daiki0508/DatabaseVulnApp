package com.websarva.wings.android.databasevulnapp.ui.fragment.realtime

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.websarva.wings.android.databasevulnapp.R
import com.websarva.wings.android.databasevulnapp.databinding.FragmentRealtimeBinding
import com.websarva.wings.android.databasevulnapp.ui.MainActivity
import com.websarva.wings.android.databasevulnapp.ui.fragment.AlertDialogFragment
import com.websarva.wings.android.databasevulnapp.viewmodel.realtime.RealTimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealTimeFragment: Fragment() {
    private var _binding: FragmentRealtimeBinding? = null
    private val binding
    get() = _binding!!

    private val viewModel: RealTimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRealtimeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // backButtonが押された時
        requireActivity().onBackPressedDispatcher.addCallback(this){
            activity?.let {
                with(Intent(it, MainActivity::class.java)){
                    it.finish()
                    startActivity(this)
                    it.overridePendingTransition(R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
                }
            }
        }

        // loginボタンタップ時の処理
        binding.button.setOnClickListener {
            viewModel.getData()
        }

        // userのobserver
        viewModel.user.observe(this.viewLifecycleOwner, { userdata ->
            activity?.let {
                AlertDialogFragment(
                    binding.edUser.text.toString() == userdata.name && binding.edPass.text.toString() == userdata.password
                ).show(it.supportFragmentManager, "RealTimeDialog")
            }
        })
    }
}
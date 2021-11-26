package com.websarva.wings.android.databasevulnapp.ui.fragment.database

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.websarva.wings.android.databasevulnapp.databinding.FragmentDatabaseBinding
import com.websarva.wings.android.databasevulnapp.ui.fragment.sharedpreference.AlertDialogFragment
import com.websarva.wings.android.databasevulnapp.viewmodel.database.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DatabaseFragment: Fragment() {
    private var _binding: FragmentDatabaseBinding? = null
    private val binding
    get() = _binding!!

    private val viewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatabaseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // databaseの作成
        viewModel.createDatabase()

        // Loginボタンタップ時の処理
        binding.button.setOnClickListener {
            viewModel.getData()
        }

        // userdataの通知
        viewModel.userData.observe(this.viewLifecycleOwner, { userdata ->
            activity?.let {
                AlertDialogFragment(
                    binding.edUser.text.toString() == userdata.name && binding.edPass.text.toString() == userdata.password
                ).show(it.supportFragmentManager, "DatabaseDialog")
            }
        })
    }
}
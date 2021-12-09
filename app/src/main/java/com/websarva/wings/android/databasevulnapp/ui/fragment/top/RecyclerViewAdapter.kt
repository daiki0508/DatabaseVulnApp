package com.websarva.wings.android.databasevulnapp.ui.fragment.top

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.databasevulnapp.R
import com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.db.ContentProvider01Fragment
import com.websarva.wings.android.databasevulnapp.ui.fragment.database.DatabaseFragment
import com.websarva.wings.android.databasevulnapp.ui.fragment.external.ExternalFragment
import com.websarva.wings.android.databasevulnapp.ui.fragment.internal.InternalFragment
import com.websarva.wings.android.databasevulnapp.ui.fragment.log.LogFragment
import com.websarva.wings.android.databasevulnapp.ui.fragment.realtime.RealTimeFragment
import com.websarva.wings.android.databasevulnapp.ui.fragment.sharedpreference.SharedPreferenceFragment

class RecyclerViewAdapter(
    private val items: List<String>,
    private val fragment: TopFragment
): RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_top, parent, false)

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.title.text = items[position]

        // recyclerViewのリストタップ時の処理
        holder.view.setOnClickListener { _ ->
            fragment.activity?.let {
                when(position){
                    0 -> {
                        transaction(it).replace(R.id.fragment_container, SharedPreferenceFragment()).commit()
                    }
                    1 -> {
                        transaction(it).replace(R.id.fragment_container, DatabaseFragment()).commit()
                    }
                    2 -> {
                        transaction(it).replace(R.id.fragment_container, RealTimeFragment()).commit()
                    }
                    3 -> {
                        transaction(it).replace(R.id.fragment_container, InternalFragment()).commit()
                    }
                    4 -> {
                        transaction(it).replace(R.id.fragment_container, ExternalFragment()).commit()
                    }
                    5 -> {
                        transaction(it).replace(R.id.fragment_container, LogFragment()).commit()
                    }
                    6 -> {
                        transaction(it).replace(R.id.fragment_container, ContentProvider01Fragment()).commit()
                    }
                    7 -> {
                        TODO("未実装")
                    }
                    else -> {
                        Log.e("ERROR", "Invalid Value.")
                        it.finish()
                    }
                }
            }
        }
    }

    private fun transaction(activity: FragmentActivity): FragmentTransaction = activity.supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.fragment_up_enter, R.anim.fragment_up_exit)

    override fun getItemCount(): Int = items.size
}
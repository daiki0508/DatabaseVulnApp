package com.websarva.wings.android.databasevulnapp.ui.fragment.top

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.databasevulnapp.R
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
                        val transaction = it.supportFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(R.anim.fragment_up_enter, R.anim.fragment_up_exit)
                        transaction.replace(R.id.fragment_container, SharedPreferenceFragment()).commit()
                    }
                    1 -> {
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

    override fun getItemCount(): Int = items.size
}
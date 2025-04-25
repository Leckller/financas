package com.example.finance.data.Holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.R
import com.google.android.material.snackbar.Snackbar

class TransactionViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val typeText: TextView = view.findViewById(R.id.transaction_type);
    val valueText: TextView = view.findViewById(R.id.transaction_value);

    init {
        view.setOnClickListener {
            Snackbar.make(view, adapterPosition.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

}
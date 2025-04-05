package com.example.finance.data.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.R
import com.example.finance.data.Holders.TransactionViewHolder
import com.example.finance.data.Transaction

class TransactionAdapter(val transactions: List<Transaction>)
    : RecyclerView.Adapter<TransactionViewHolder>() {

    // cria o item
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false);

        return TransactionViewHolder(view);
    }

    // atualiza os itens da lista
    override fun onBindViewHolder(
        holder: TransactionViewHolder,
        position: Int
    ) {
        val transaction = transactions[position]
        holder.typeText.text = transaction.type;
        holder.valueText.text = transaction.value.toString();
    }

    override fun getItemCount(): Int {
        return transactions.size;
    }

}
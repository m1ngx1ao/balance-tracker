package app.balancetracker.controller

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.balancetracker.R

class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val layout: View = view.findViewById(R.id.transactionListItemLayout)
    val amountTextView: TextView = view.findViewById(R.id.transactionListItemAmountTextView)
    val descriptionTextView: TextView = view.findViewById(R.id.transactionListItemDescriptionTextView)
    val dateTextView: TextView = view.findViewById(R.id.transactionListItemDateTextView)
}

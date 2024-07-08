package app.balancetracker.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.balancetracker.R
import app.balancetracker.controller.reuse.CustomToast
import app.balancetracker.model.Amount
import app.balancetracker.model.Transaction
import app.balancetracker.persistence.Storage

class TransactionAdapter(
    private val context: Context,
    private val storage: Storage,
    private val customToast: CustomToast,
    private var transactionsBuffer: List<Transaction> = storage.transactions
) : RecyclerView.Adapter<TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.transaction_list_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionsBuffer[position]
        holder.layout.setBackgroundColor(context.getColor(
            if (transaction.amount > Amount()) R.color.c64_pseudo_darkGreen else R.color.c64_brown
        ))
        holder.amountTextView.text = transaction.amount.toString()
        holder.descriptionTextView.text = transaction.description
        holder.dateTextView.text = transaction.getFormattedDate()
    }

    override fun getItemCount() = transactionsBuffer.size
    fun removeItem(position: Int) {
        val transaction = transactionsBuffer[position]
        transactionsBuffer = transactionsBuffer.toMutableList().apply {
            removeAt(position)
        }
        notifyItemRemoved(position)
        storage.balance -= transaction.amount
        storage.transactions = transactionsBuffer
        customToast
            .show(context.getString(
                if (transaction.description.isEmpty()) R.string.toast_canceled_amount
                else R.string.toast_canceled_amount_with_description,
                transaction.amount.toString(),
                transaction.description
            ))
    }
}

package app.balancetracker.controller

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.balancetracker.R
import app.balancetracker.controller.reuse.CustomToast
import app.balancetracker.persistence.Storage

class TransactionListActivity : Activity() {
    private val storage: Storage by lazy { Storage(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        val recyclerView = findViewById<RecyclerView>(R.id.transactionRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val transactionAdapter = TransactionAdapter(
            this,
            storage,
            CustomToast(applicationContext, layoutInflater)
        )
        recyclerView.adapter = transactionAdapter

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false // No move supported
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    transactionAdapter.removeItem(viewHolder.getAbsoluteAdapterPosition())
                }
            }
        )
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
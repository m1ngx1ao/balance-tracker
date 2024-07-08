package app.balancetracker.controller

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import app.balancetracker.R
import app.balancetracker.controller.reuse.CustomToast
import app.balancetracker.model.Amount
import app.balancetracker.model.Transaction
import app.balancetracker.persistence.Storage

class MainActivity : Activity() {
    private val storage: Storage by lazy { Storage(this) }
    private val customToast: CustomToast by lazy {
        CustomToast(applicationContext, layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.settleAccountButton).setOnClickListener {
            showSettleAccountDialog()
        }
        findViewById<Button>(R.id.bookAnyButton).setOnClickListener {
            showBookDialog()
        }
        findViewById<Button>(R.id.viewAllTransactionsButton).setOnClickListener {
            startActivity(Intent(this, TransactionListActivity::class.java))
        }
        refreshTemplates()
        refreshBalanceAndTransactions()
    }

    override fun onResume() {
        super.onResume()
        refreshBalanceAndTransactions()
    }

    private fun setTopMargin(v: View, valueInDp: Int) {
        val layoutParams = v.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(
            layoutParams.leftMargin,
            (valueInDp * resources.displayMetrics.density).toInt(),
            layoutParams.rightMargin,
            layoutParams.bottomMargin
        )
        v.layoutParams = layoutParams
    }

    private fun refreshTemplates() {
        val templatesLinearLayout = findViewById<LinearLayout>(R.id.templatesLinearLayout)
        templatesLinearLayout.removeAllViews()
        for (template in storage.templates) {
            val button = Button(this)
            button.text = template.description
            button.setOnClickListener {
                postTransaction(template.amount, template.description)
            }
            button.setOnLongClickListener {
                showDeleteTemplateDialog(template)
                true
            }
            templatesLinearLayout.addView(button)
            setTopMargin(button, 8)
        }
    }

    private fun refreshBalanceAndTransactions() {
        val balanceTextView = findViewById<TextView>(R.id.balanceTextView)
        balanceTextView.text = storage.balance.toString()

        val transactionsTextView = findViewById<TextView>(R.id.transactionsTextView)
        val transactionsText = storage.transactions.joinToString("\n") {
            getString(
                if (it.description.isEmpty()) R.string.transaction_log_amount
                else R.string.transaction_log_amount_with_description,
                it.amount.toString(),
                it.description,
                it.getFormattedDate()
            )
        }
        transactionsTextView.text = transactionsText
    }

    private fun showBookDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_book_any, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = builder.show()

        dialogView.findViewById<Button>(R.id.bookAnyBookButton).setOnClickListener {
            val amountText = dialogView.findViewById<EditText>(R.id.bookAnyAmountEditText).text.toString()
            val description = dialogView.findViewById<EditText>(R.id.bookAnyDescriptionEditText).text.toString()
            postTransaction(Amount(amountText), description)
            if (dialogView.findViewById<CheckBox>(R.id.saveAsTemplateCheckBox).isChecked) {
                storage.templates += Transaction(Amount(amountText), description)
                refreshTemplates()
            }
            alertDialog.dismiss()
        }
    }

    private fun showSettleAccountDialog() {
        val alertDialog = AlertDialog.Builder(this, R.style.SettleAccountDialogTheme)
            // alert title not affected by theme capitalization
            .setTitle(getString(R.string.settle_account).uppercase())
            .setMessage(getString(R.string.settle_account_message))
            .setPositiveButton(getString(R.string.affirmative)) { _, _ ->
                storage.balance = Amount()
                storage.transactions = emptyList()
                refreshBalanceAndTransactions()
                CustomToast(applicationContext, layoutInflater)
                    .show(getString(R.string.toast_balance_settled), R.color.c64_lightRed, R.color.c64_red)
            }
            .setNegativeButton(getString(R.string.negative), null)
            .show()
        alertDialog.findViewById<TextView>(android.R.id.message).typeface =
            ResourcesCompat.getFont(this, R.font.c64_rounded)
    }

    private fun showDeleteTemplateDialog(template: Transaction) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_template, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = builder.show()

        dialogView.findViewById<TextView>(R.id.deleteTemplateAmountTextView).text = template.amount.toString()
        dialogView.findViewById<TextView>(R.id.deleteTemplateDescriptionTextView).text = template.description
        dialogView.findViewById<Button>(R.id.deleteTemplateButton).setOnClickListener {
            storage.templates = storage.templates.filter { it != template }
            refreshTemplates()
            customToast.show(getString(
                R.string.toast_template_deleted,
                template.amount.toString(),
                template.description
            ), R.color.c64_lightBlue, R.color.c64_blue)
            alertDialog.dismiss()
        }
    }

    private fun postTransaction(amount: Amount, description: String) {
        storage.balance += amount
        storage.transactions = listOf(Transaction(amount, description)) + storage.transactions
        CustomToast(applicationContext, layoutInflater)
            .show(getString(
                if (description.isEmpty()) R.string.toast_booked_amount
                else R.string.toast_booked_amount_with_description, amount.toString(), description
            ))
        refreshBalanceAndTransactions()
    }
}

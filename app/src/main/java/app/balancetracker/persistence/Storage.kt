package app.balancetracker.persistence

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import app.balancetracker.model.Amount
import app.balancetracker.model.Transaction
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.LinkedList

class Storage(context: Context) {
    private val prefsName = "balance_tracker_prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    private val balanceKey = "balance"
    var balance: Amount
        get() = Amount(prefs.getInt(balanceKey, 0))
        set(amount) = prefs.edit().putInt(balanceKey, amount.value).apply()

    private val templatesKey = "templates"
    var templates: List<Transaction>
        get() = keyToTxList(templatesKey)
        set(value) = storeTxListForKey(templatesKey, value)

    private val transactionsKey = "transactions"
    var transactions: List<Transaction>
        get() = keyToTxList(transactionsKey)
        set(value) = storeTxListForKey(transactionsKey, value)

    private fun keyToTxList(key: String): List<Transaction> {
        val transactionsSerialized = prefs.getString(key, null)
        try {
            val byteArray = Base64.decode(transactionsSerialized, Base64.DEFAULT)
            val objectInputStream = ObjectInputStream(ByteArrayInputStream(byteArray))
            @Suppress("UNCHECKED_CAST")
            val result = objectInputStream.readObject() as List<Transaction>
            objectInputStream.close()
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return LinkedList<Transaction>()
    }

    private fun storeTxListForKey(key: String, txList: List<Transaction>) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(txList)
        objectOutputStream.close()
        val transactionsSerialized = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
        prefs.edit().putString(key, transactionsSerialized).apply()
    }
}

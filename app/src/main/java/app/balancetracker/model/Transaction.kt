package app.balancetracker.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Transaction(val amount: Amount, val description: String = "", val date: Date = Date())
    : Serializable {
    fun getFormattedDate(): String {
        val dateFormatter = SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.US)
        return dateFormatter.format(date)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Transaction) {
            return amount == other.amount
                    && description == other.description
                    && date == other.date
        }
        return false
    }

    override fun hashCode(): Int {
        return (getFormattedDate() + amount + description).hashCode()
    }
}
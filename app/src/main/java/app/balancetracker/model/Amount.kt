package app.balancetracker.model

import java.io.Serializable
import java.math.BigDecimal
import java.util.Locale

class Amount(public val value: Int = 0): Serializable {
    constructor(amountStr: String): this((BigDecimal(amountStr) * BigDecimal(100.0)).toInt())
    public override fun toString(): String {
        return String.format(Locale.US, "%.2f", value / 100.0)
    }
    operator fun plus(other: Amount) = Amount(value + other.value)
    operator fun minus(other: Amount) = Amount(value - other.value)
    operator fun compareTo(other: Amount) = value.compareTo(other.value)

    override fun equals(other: Any?): Boolean {
        if (other is Amount) {
            return value == other.value
        }
        return false
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
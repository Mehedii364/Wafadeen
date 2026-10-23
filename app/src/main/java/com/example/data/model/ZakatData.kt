package com.example.data.model

data class ZakatInput(
    val goldValue: Double = 0.0,
    val silverValue: Double = 0.0,
    val cashInHandAndBank: Double = 0.0,
    val businessMerchandiseValue: Double = 0.0,
    val loanReceivable: Double = 0.0,
    val immediateDebtsPayable: Double = 0.0,
    val silverNisabPricePerGram: Double = 120.0, // approx BDT / gram
    val goldNisabPricePerGram: Double = 11500.0,
    val selectedNisabType: NisabType = NisabType.SILVER
)

enum class NisabType {
    SILVER, // 595 grams (More beneficial for the poor - Jamhur view)
    GOLD    // 85 grams
}

data class ZakatResult(
    val netWealth: Double,
    val nisabThreshold: Double,
    val isZakatObligatory: Boolean,
    val zakatPayable: Double,
    val currencySymbol: String = "৳"
)

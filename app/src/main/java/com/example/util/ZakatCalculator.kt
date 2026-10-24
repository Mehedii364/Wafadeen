package com.example.util

import com.example.data.model.NisabType
import com.example.data.model.ZakatInput
import com.example.data.model.ZakatResult
import kotlin.math.max

object ZakatCalculator {

    const val SILVER_NISAB_GRAMS = 595.0
    const val GOLD_NISAB_GRAMS = 85.0
    const val ZAKAT_RATE = 0.025 // 2.5%

    /**
     * Calculates Zakat obligation, threshold, and amount payable
     */
    fun calculateZakat(input: ZakatInput): ZakatResult {
        val nisabThreshold = when (input.selectedNisabType) {
            NisabType.SILVER -> SILVER_NISAB_GRAMS * input.silverNisabPricePerGram
            NisabType.GOLD -> GOLD_NISAB_GRAMS * input.goldNisabPricePerGram
        }

        val grossAssets = input.cashInHandAndBank +
                input.goldValue +
                input.silverValue +
                input.businessMerchandiseValue +
                input.loanReceivable

        val netWealth = max(0.0, grossAssets - input.immediateDebtsPayable)
        val isObligatory = netWealth >= nisabThreshold
        val zakatPayable = if (isObligatory) netWealth * ZAKAT_RATE else 0.0

        return ZakatResult(
            netWealth = netWealth,
            nisabThreshold = nisabThreshold,
            isZakatObligatory = isObligatory,
            zakatPayable = zakatPayable
        )
    }

    /**
     * Direct calculation helper for given values
     */
    fun calculate(
        cash: Double,
        goldVal: Double,
        silverVal: Double,
        merchandise: Double,
        receivables: Double,
        debts: Double,
        goldGramPrice: Double = 11500.0,
        silverGramPrice: Double = 120.0,
        nisabType: NisabType = NisabType.SILVER
    ): ZakatResult {
        return calculateZakat(
            ZakatInput(
                goldValue = goldVal,
                silverValue = silverVal,
                cashInHandAndBank = cash,
                businessMerchandiseValue = merchandise,
                loanReceivable = receivables,
                immediateDebtsPayable = debts,
                goldNisabPricePerGram = goldGramPrice,
                silverNisabPricePerGram = silverGramPrice,
                selectedNisabType = nisabType
            )
        )
    }
}

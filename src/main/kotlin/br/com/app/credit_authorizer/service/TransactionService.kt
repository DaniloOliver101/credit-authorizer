package br.com.app.credit_authorizer.service

import br.com.app.credit_authorizer.dto.TransactionDto
import br.com.app.credit_authorizer.exception.NotFoundException
import br.com.app.credit_authorizer.model.Account
import br.com.app.credit_authorizer.model.Merchant
import br.com.app.credit_authorizer.model.ResponseCode
import br.com.app.credit_authorizer.model.Transaction
import br.com.app.credit_authorizer.repository.AccountRepository
import br.com.app.credit_authorizer.repository.MerchantRepository
import br.com.app.credit_authorizer.repository.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService(
    val accountRepository: AccountRepository,
    val transactionRepository: TransactionRepository,
    val merchantRepository: MerchantRepository
) {

    @Transactional
    fun processTransaction(transactionDto: TransactionDto): Map<String, String> {
        val account = accountRepository.findById(transactionDto.account)
            .orElseThrow { NotFoundException("Account not found") }

        val merchantName = transactionDto.merchant.uppercase(Locale.getDefault())
        val mcc = merchantRepository.findMccByName(merchantName) ?: transactionDto.mcc

        val merchant = Merchant(mcc = mcc, name = transactionDto.merchant)
        val responseCode = authorizeTransaction(transactionDto.totalAmount, merchant, account)

        val transaction = account.id?.let {
            Transaction(
                accountId = it,
                mcc = mcc,
                totalAmount = transactionDto.totalAmount,
                merchant = transactionDto.merchant,
                code = responseCode.code
            )
        }
        if (transaction != null) {
            transactionRepository.save(transaction)
        }
        return mapOf("code" to responseCode.code)
    }

    private fun authorizeTransaction(totalAmount: Double, merchant: Merchant, account: Account): ResponseCode {
        val mcc = merchant.mcc
        return if (!mcc.isNullOrBlank()) {
            when (mcc) {
                "5412", "5411" -> processFoodTransaction(totalAmount, account)
                "5812", "5811" -> processMealTransaction(totalAmount, account)
                else -> processCashTransaction(totalAmount, account)
            }
        } else {
            processCashTransaction(totalAmount, account)
        }
    }

    private fun processFoodTransaction(totalAmount: Double, account: Account): ResponseCode {
        return if (account.foodBalance >= totalAmount) {
            account.foodBalance -= totalAmount
            accountRepository.save(account)
            ResponseCode.APPROVED
        } else if (account.foodBalance + account.cashBalance >= totalAmount) {
            val remainingAmount = totalAmount - account.foodBalance
            account.cashBalance -= remainingAmount
            account.foodBalance = 0.0
            accountRepository.save(account)
            ResponseCode.APPROVED
        } else {
            ResponseCode.INSUFFICIENT_FUNDS
        }
    }

    private fun processMealTransaction(totalAmount: Double, account: Account): ResponseCode {
        return if (account.mealBalance >= totalAmount) {
            account.mealBalance -= totalAmount
            accountRepository.save(account)
            ResponseCode.APPROVED
        } else if (account.mealBalance + account.cashBalance >= totalAmount) {
            val remainingAmount = totalAmount - account.mealBalance
            account.cashBalance -= remainingAmount
            account.mealBalance = 0.0
            accountRepository.save(account)
            ResponseCode.APPROVED
        } else {
            ResponseCode.INSUFFICIENT_FUNDS
        }
    }

    private fun processCashTransaction(totalAmount: Double, account: Account): ResponseCode {
        return if (account.cashBalance >= totalAmount) {
            account.cashBalance -= totalAmount
            accountRepository.save(account)
            ResponseCode.APPROVED
        } else {
            ResponseCode.INSUFFICIENT_FUNDS
        }
    }

    fun getTransactions(): MutableList<Transaction> {
        return transactionRepository.findAll()
    }
}
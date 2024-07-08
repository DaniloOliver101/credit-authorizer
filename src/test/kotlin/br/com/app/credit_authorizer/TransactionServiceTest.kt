package br.com.app.credit_authorizer

import br.com.app.credit_authorizer.dto.TransactionDto
import br.com.app.credit_authorizer.exception.NotFoundException
import br.com.app.credit_authorizer.model.Account
import br.com.app.credit_authorizer.model.ResponseCode
import br.com.app.credit_authorizer.repository.AccountRepository
import br.com.app.credit_authorizer.repository.MerchantRepository
import br.com.app.credit_authorizer.repository.TransactionRepository
import br.com.app.credit_authorizer.service.TransactionService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class TransactionServiceTest {

    private val accountRepository: AccountRepository = mock()
    private val transactionRepository: TransactionRepository = mock()
    private val merchantRepository: MerchantRepository = mock()
    private lateinit var transactionService: TransactionService

    @BeforeEach
    fun setUp() {
        transactionService = TransactionService(accountRepository, transactionRepository, merchantRepository)
    }

    @Test
    fun `processTransaction should approve transaction with mcc 5412`() {
        val account = Account(id = 1, foodBalance = 100.0, mealBalance = 50.0, cashBalance = 200.0)
        val transactionDto = TransactionDto(account = 1, mcc = "5412", merchant = "Test Merchant", totalAmount = 30.0)

        whenever(accountRepository.findById(1)).thenReturn(Optional.of(account))

        val response = transactionService.processTransaction(transactionDto)

        assertEquals(ResponseCode.APPROVED.code, response["code"])
        assertEquals(70.0, account.foodBalance)
        assertEquals(200.0, account.cashBalance)
    }

    @Test
    fun `processTransaction should approve transaction with mcc 5812`() {
        val account = Account(id = 1, foodBalance = 100.0, mealBalance = 50.0, cashBalance = 200.0)
        val transactionDto = TransactionDto(account = 1, mcc = "5812", merchant = "Test Merchant", totalAmount = 30.0)

        whenever(accountRepository.findById(1)).thenReturn(Optional.of(account))

        val response = transactionService.processTransaction(transactionDto)

        assertEquals(ResponseCode.APPROVED.code, response["code"])
        assertEquals(20.0, account.mealBalance)
        assertEquals(200.0, account.cashBalance)
    }

    @Test
    fun `processTransaction should deny transaction with insufficient funds`() {
        val account = Account(id = 1, foodBalance = 10.0, mealBalance = 10.0, cashBalance = 10.0)
        val transactionDto = TransactionDto(account = 1, mcc = "5412", merchant = "Test Merchant", totalAmount = 50.0)

        whenever(accountRepository.findById(1)).thenReturn(Optional.of(account))

        val response = transactionService.processTransaction(transactionDto)

        assertEquals(ResponseCode.INSUFFICIENT_FUNDS.code, response["code"])
        assertEquals(10.0, account.foodBalance)
        assertEquals(10.0, account.cashBalance)
    }

    @Test
    fun `processTransaction should throw NotFoundException for non-existent account`() {
        val transactionDto = TransactionDto(account = 1, mcc = "5412", merchant = "Test Merchant", totalAmount = 30.0)

        whenever(accountRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(NotFoundException::class.java) {
            transactionService.processTransaction(transactionDto)
        }
    }

    @Test
    fun `processTransaction should approve transaction with non-configured mcc using cashBalance`() {
        val account = Account(id = 1, foodBalance = 100.0, mealBalance = 50.0, cashBalance = 200.0)
        val transactionDto = TransactionDto(account = 1, mcc = "1234", merchant = "Test Merchant", totalAmount = 30.0)

        whenever(accountRepository.findById(1)).thenReturn(Optional.of(account))

        val response = transactionService.processTransaction(transactionDto)

        assertEquals(ResponseCode.APPROVED.code, response["code"])
        assertEquals(170.0, account.cashBalance)
    }

    @Test
    fun `processTransaction should substitute mcc based on merchant name from database`() {
        val account = Account(id = 1, foodBalance = 100.0, mealBalance = 50.0, cashBalance = 200.0)
        val transactionDto = TransactionDto(account = 1, mcc = "9999", merchant = "UBER EATS", totalAmount = 30.0)

        whenever(accountRepository.findById(1)).thenReturn(Optional.of(account))
        whenever(merchantRepository.findMccByName("UBER EATS")).thenReturn("5812")

        val response = transactionService.processTransaction(transactionDto)

        assertEquals(ResponseCode.APPROVED.code, response["code"])
        assertEquals(20.0, account.mealBalance)
        assertEquals(200.0, account.cashBalance)
    }
}

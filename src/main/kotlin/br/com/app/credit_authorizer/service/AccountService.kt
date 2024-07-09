package br.com.app.credit_authorizer.service

import br.com.app.credit_authorizer.model.Account
import br.com.app.credit_authorizer.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(val accountRepository: AccountRepository) {
    private val logger = LoggerFactory.getLogger(AccountService::class.java)

    fun createAccount(account: Account): Account {
        logger.info("Creating account with data: {}", account)
        val savedAccount = accountRepository.save(account)
        logger.info("Account created successfully with id: {}", savedAccount.id)
        return savedAccount
    }

    fun getBalance(accountId: Long): Optional<Account> {
        logger.info("Fetching balance for account id: {}", accountId)
        val account = accountRepository.findById(accountId)
        if (account.isPresent) {
            logger.info("Balance fetched successfully for account id: {}", accountId)
        } else {
            logger.warn("Account not found for id: {}", accountId)
        }
        return account
    }
}

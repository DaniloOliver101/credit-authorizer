package br.com.app.credit_authorizer.service

import br.com.app.credit_authorizer.model.Account
import br.com.app.credit_authorizer.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(val accountRepository: AccountRepository) {


    fun createAccount(account: Account): Account {
return         accountRepository.save(account)


    }

    fun getBalance(accountId: Long): Optional<Account> {
        return accountRepository.findById(accountId)

    }
}
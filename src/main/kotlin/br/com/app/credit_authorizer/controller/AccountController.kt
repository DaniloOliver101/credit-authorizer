package br.com.app.credit_authorizer.controller

import br.com.app.credit_authorizer.model.Account
import br.com.app.credit_authorizer.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/v1/account")
class AccountController(
    val accountService: AccountService
) {
    private val logger = LoggerFactory.getLogger(AccountController::class.java)

    @PostMapping("/")
    fun createAccount(@RequestBody account: Account): ResponseEntity<Account> {
        return try {

            val createdAccount = accountService.createAccount(account)
            ResponseEntity.status(HttpStatus.CREATED).body(createdAccount)
        } catch (e: Exception) {
            logger.error("Erro ao criar conta: ${e.message}", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/balance/{accountId}")
    fun getBalance(@PathVariable accountId: Long): ResponseEntity<Account> {
        return try {
            val account = accountService.getBalance(accountId)
            if (account.isPresent) {
                ResponseEntity.ok(account.get())
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
        } catch (e: Exception) {
            logger.error("Erro ao obter saldo da conta: ${e.message}", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}

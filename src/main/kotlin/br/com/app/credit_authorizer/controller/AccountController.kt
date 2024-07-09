package br.com.app.credit_authorizer.controller

import br.com.app.credit_authorizer.model.Account
import br.com.app.credit_authorizer.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/account")
class AccountController(
    val accountService: AccountService
) {
    private val logger = LoggerFactory.getLogger(AccountController::class.java)

    @Operation(summary = "Create a new account", description = "Creates a new account and returns the created account details")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Account created successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Account::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
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

    @Operation(summary = "Get account balance", description = "Returns the balance of the account with the given ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Account found",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Account::class))]),
        ApiResponse(responseCode = "404", description = "Account not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
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

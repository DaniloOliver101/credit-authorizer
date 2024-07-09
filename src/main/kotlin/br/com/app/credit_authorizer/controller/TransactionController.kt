package br.com.app.credit_authorizer.controller

import br.com.app.credit_authorizer.dto.TransactionDto
import br.com.app.credit_authorizer.model.Transaction
import br.com.app.credit_authorizer.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/transaction")
@Tag(name = "Transaction Controller", description = "Api que processa a transação")
class TransactionController(val transactionService: TransactionService) {
    private val logger = LoggerFactory.getLogger(TransactionController::class.java)

    @PostMapping("/")
    @Operation(
        summary = "Enviar Transação",
        description = "Processa uma transação e retorna o código de resposta",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Transação processada com sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Map::class))]
            )
        ]
    )
    fun sendTransaction(@RequestBody transaction: TransactionDto): ResponseEntity<Map<String, String>> {
        logger.info("TransactionController - sendTransaction: starting transaction process : $transaction")
        val processTransaction = transactionService.processTransaction(transaction)
        logger.info("TransactionController - sendTransaction:  Finished with CODE: : ${processTransaction["code"]}")
        return ResponseEntity.status(HttpStatus.OK).body(processTransaction)
    }

    @GetMapping("/")
    @Operation(
        summary = "Obter Transações",
        description = "Retorna uma lista de todas as transações",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Transações recuperadas com sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = List::class))]
            )
        ]
    )
    fun getTransactions(): ResponseEntity<MutableList<Transaction>> {
        logger.info("TransactionController - getTransactions: Listando todas as transações")
        val transactions = transactionService.getTransactions()
        logger.info("TransactionController - getTransactions: Encontradas ${transactions.size} transações")
        return ResponseEntity.status(HttpStatus.OK).body(transactions)
    }
}

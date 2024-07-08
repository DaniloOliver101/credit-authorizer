package br.com.app.credit_authorizer.controller

import br.com.app.credit_authorizer.dto.TransactionDto
import br.com.app.credit_authorizer.model.Transaction
import br.com.app.credit_authorizer.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/transaction")
class TransactionController(val transactionService: TransactionService) {
    @PostMapping("/")
    fun sendTransaction(@RequestBody transaction: TransactionDto): ResponseEntity<Map<String, String>> {
        print("Post recebido")
        val processTransaction = transactionService.processTransaction(transaction)
        return ResponseEntity.status(HttpStatus.OK).body(processTransaction)
    }

    @GetMapping("/")
    fun getTransactions(): ResponseEntity<MutableList<Transaction>> {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactions())

    }
}
package br.com.app.credit_authorizer.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


data class TransactionDto(
    @field:NotBlank(message = "A conta não pode ser vazia.")
    val account: Long,

    @field:NotNull(message = "O valor total não pode ser nulo.")
    @field:DecimalMin(value = "0.01", message = "O valor total deve ser maior que 0.")
    val totalAmount: Double,


    val mcc: String,

    @field:NotBlank(message = "O comerciante não pode ser vazio.")
    val merchant: String,
)
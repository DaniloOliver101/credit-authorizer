package br.com.app.credit_authorizer.model

import jakarta.persistence.Entity


@Entity
data class Account(
    val accountId: Long,
    var foodBalance: Double = 500.00,
    var mealBalance: Double = 500.00,
    var cashBalance: Double = 1000.00
)

package br.com.app.credit_authorizer.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var foodBalance: Double = 500.00,
    var mealBalance: Double = 500.00,
    var cashBalance: Double = 1000.00
)

package br.com.app.credit_authorizer.model


import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID


@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    val accountId: Long,
    val totalAmount: Double,
    val mcc: String,
    val merchant: String,
    val code: String?
)
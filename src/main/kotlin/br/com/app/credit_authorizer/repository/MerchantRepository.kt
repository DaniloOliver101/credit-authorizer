package br.com.app.credit_authorizer.repository

import br.com.app.credit_authorizer.model.Merchant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MerchantRepository : JpaRepository<Merchant, Long> {
    @Query("SELECT m.mcc FROM Merchant m WHERE m.name = :name")
    fun findMccByName(@Param("name") name: String): String?
}

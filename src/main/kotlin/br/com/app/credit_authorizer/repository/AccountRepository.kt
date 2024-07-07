package br.com.app.credit_authorizer.repository

import br.com.app.credit_authorizer.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    // Métodos de consulta específicos podem ser adicionados aqui, se necessário
}

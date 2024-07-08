package br.com.app.credit_authorizer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CreditAuthorizerApplication

fun main(args: Array<String>) {
	runApplication<CreditAuthorizerApplication>(*args)
}

package br.com.app.credit_authorizer.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/account")
class AccountController {

    @GetMapping("/balance/{account-id}")
    fun getBalance(@PathVariable accountId: Long){

    }
}
package com.github.jntakpe.webfluxbugreport

import com.fasterxml.jackson.annotation.JsonView
import com.github.jntakpe.webfluxbugreport.WebfluxBugReportApplication.Views.Internal
import com.github.jntakpe.webfluxbugreport.WebfluxBugReportApplication.Views.Public
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@SpringBootApplication
class WebfluxBugReportApplication {

    fun main(args: Array<String>) {
        runApplication<WebfluxBugReportApplication>(*args)
    }

    @RestController
    @RequestMapping("/users")
    class UserController {

        @JsonView(Public::class)
        @GetMapping("/{username}")
        fun findPublic(@PathVariable username: String): Mono<User> = User(username, "pwd").toMono()

        @JsonView(Internal::class)
        @GetMapping("/{username}", params = arrayOf("withpwd"))
        fun findInternal(@PathVariable username: String): Mono<User> = User(username, "pwd").toMono()

    }

    data class User(val username: String, @field:JsonView(Internal::class) val password: String?)

    interface Views {

        interface Public

        interface Internal : Public
    }
}



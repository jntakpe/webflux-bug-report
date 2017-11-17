package com.github.jntakpe.webfluxbugreport

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@ExtendWith(SpringExtension::class)
class WebfluxBugReportApplicationTests {

    private lateinit var webTestClient: WebTestClient

    @Test
    fun contextLoads() {
    }

    @BeforeEach
    fun beforeEach(context: ReactiveWebApplicationContext) {
        webTestClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .build()
    }


    @Test
    fun `find by username without password`() {
        webTestClient.get()
                .uri("/users/{username}", "john")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .consumeWith {
                    val user: WebfluxBugReportApplication.User = jacksonObjectMapper().readValue(it.responseBody!!)
                    Assertions.assertThat(user.username).isNotNull()
                    Assertions.assertThat(user.password).isNull()
                }
    }

    @Test
    fun `find by username with password`() {
        webTestClient.get()
                .uri("/users/{username}?withpwd=true", "john")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .consumeWith {
                    val user: WebfluxBugReportApplication.User = jacksonObjectMapper().readValue(it.responseBody!!)
                    Assertions.assertThat(user.username).isNotNull()
                    Assertions.assertThat(user.password).isNotNull()
                }
    }

}

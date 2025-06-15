package com.boot.security.auth;

import com.boot.security.user.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.boot.UtilsTest.generateUUID;
import static java.lang.Thread.sleep;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    private WebTestClient testClient;

    @Test
    @DisplayName("deve registrar um usuário com sucesso")
    void register() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("admin" + generateUUID());
        request.setLastname("admin" + generateUUID());
        request.setEmail("admin" + generateUUID() + "@mail.com");
        request.setPassword("password");

        AuthenticationResponse responseBody = testClient.post()
                .uri("/api/v1/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AuthenticationResponse.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getAccessToken());
        Assertions.assertNotNull(responseBody.getRefreshToken());
    }

    @Test
    @DisplayName("deve retornar erro ao registrar usuário com email já existente")
    void registerWithExistingEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("existing" + generateUUID());
        request.setLastname("user");
        request.setEmail("existing" + generateUUID() + "@mail.com");
        request.setPassword("password");

        authService.register(request); // Primeiro registro deve ser bem-sucedido

        testClient.post()
                .uri("/api/v1/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(422) // Espera erro de requisição inválida
                .expectBody(String.class)
                .value(errorMessage -> Assertions.assertTrue(
                        errorMessage.contains("Email already exists"),
                        "Expected error message for existing email"
                ));
    }

}
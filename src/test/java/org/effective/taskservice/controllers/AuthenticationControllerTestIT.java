package org.effective.taskservice.controllers;

import org.effective.taskservice.domain.dto.RegisterRequest;
import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.repositories.PersonRepo;
import org.effective.taskservice.services.AuthenticationService;
import org.effective.taskservice.util.exceptions.EmailNotUniqueException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class AuthenticationControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonRepo personRepo;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    @Transactional
    @Test
    @DisplayName(value = "Проверяет, что при регистрации пользователя с уникальными данными мы получаем токен, статус код 201, формат данных и то, что запись была добавлена в БД")
    void register_EmailIsUnique_returns201() throws Exception {
        //given
        var request = MockMvcRequestBuilders.post("/auth/registration")
                .contentType("application/json")
                .content("""
                        {
                        "username" : "username",
                        "email" : "email",
                        "password" : "password"
                        }
                        """);
        //when
        mockMvc.perform(request)
                //then
                .andExpectAll(status().isCreated(),
                        content().contentType("application/json"),
                        jsonPath("$.token").exists()
                );
        assertEquals(1, personRepo.findAll().size());
        var person = personRepo.findByEmail("email");
        assertNotNull(person.get());
        assertEquals("username", person.get().getUsername());
        assertNotEquals("password", person.get().getPassword());
        assertNotEquals(0, person.get().getId());
    }
    @Transactional
    @Test
    void register_EmailAlreadyExists_returns400 () throws Exception {
        //given
        personRepo.save(Person.builder()
                .username("username")
                .email("email")
                .password("password")
                .build());
        var request = MockMvcRequestBuilders.post("/auth/registration")
                .contentType("application/json")
                .content("""
                        {
                        "username" : "username",
                        "email" : "email",
                        "password" : "password"
                        }
                        """);
        //when
        mockMvc.perform(request)
                //then
                .andExpectAll(status().isBadRequest(),
                        content().contentType("application/json"),
                        content().json("""
                                {
                                "fieldName":"email",
                                "message":"Email is already used: email"
                                }
                                """),
                        jsonPath("$.token").doesNotExist()
                );
        assertEquals(0, personRepo.findAll().size());
    }

}
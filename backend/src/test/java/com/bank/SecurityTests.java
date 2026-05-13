package com.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Imports get() for mock HTTP GET requests
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Imports status() for response status assertions
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl; // Imports redirectedUrl() for redirect assertions
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class SecurityTests {
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // Test 1: unauthenticated user gets redirected
    @Test
    void unauthenticated_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void user_cannot_access_admin_page() throws Exception {
        mockMvc.perform(get("/customers/add"))
                .andExpect(status().isForbidden());
    }

    // Test 3: ADMIN can access ADMIN page
    @Test
    @WithMockUser(roles = "ADMIN")
    void admin_can_access_admin_page() throws Exception {
        mockMvc.perform(get("/customers/add"))
                .andExpect(status().isOk());
    }

    // Test 4: POST without CSRF is rejected
    @Test
    @WithMockUser(roles = "ADMIN")
    void post_without_csrf_is_forbidden() throws Exception {
        mockMvc.perform(post("/customers/add"))
                .andExpect(status().isForbidden());
    }
}
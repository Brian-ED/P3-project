package com.example.application;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;



@SpringBootTest(
    classes = Application.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
)
public abstract class TestHelperFunctions {

    @LocalServerPort
    protected int port;

    protected static Playwright playwright;
    protected static Browser browser;

     @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        @Primary
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/logout").permitAll()
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for easier testing
            return http.build();
        }
    }
    
    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    // Helper method to perform login
    protected void login(Page page, String username, String password) {
        page.navigate("http://localhost:" + port + "/login");
        page.waitForSelector("vaadin-login-form", new Page.WaitForSelectorOptions().setTimeout(10000));
        page.waitForTimeout(200);
        page.keyboard().type(username);
        page.keyboard().press("Tab");
        page.keyboard().type(password);
        page.keyboard().press("Enter");
        page.waitForTimeout(200);
    }
}
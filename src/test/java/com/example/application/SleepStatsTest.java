package com.example.application;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(
    classes = Application.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Tag("playwright")
public class SleepStatsTest {

    @LocalServerPort
    private int port;

    private static Playwright playwright;
    private static Browser browser;

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        @Primary
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());
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

    @Test
    public void testLoginPageLoads() {
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        
        try {
            // Navigate to the login page
            page.navigate("http://localhost:" + port + "/login");
            
            // Wait for Vaadin to load - look for the vaadin-login-form element
            page.waitForSelector("vaadin-login-form", new Page.WaitForSelectorOptions().setTimeout(10000));
            
            // Now check assertions
            assertThat(page.locator("vaadin-login-form")).isVisible();
            
            // Optional: check the page title if your LoginView sets one
            // assertThat(page).hasTitle("Login");
            
        } finally {
            page.close();
            context.close();
        }
    }
    /* 
    @Test
    public void testLoginFormFields() {
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        page.navigate("http://localhost:" + port + "/login");

        // Verify username field exists
        assertThat(page.locator("vaadin-text-field[name='username']")).isVisible();

        // Verify password field exists
        assertThat(page.locator("vaadin-password-field[name='password']")).isVisible();

        // Verify login button exists
        assertThat(page.locator("vaadin-button[type='submit']")).isVisible();

        browser.close();
    }

    @Test
    public void testLoginError() {
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        
        // Navigate to login page with error parameter
        page.navigate("http://localhost:" + port + "/login?error");

        // Verify error message is displayed
        assertThat(page.locator("vaadin-login-form[error]")).isVisible();

        browser.close();
    }

    @Test
    public void testSurveyAnswerDialog() {
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        page.navigate("http://localhost:" + port + "/sleep-stats");

        // Verify the page title/heading is visible
        assertThat(page.getByText("Søvnundersøgelse Svar")).isVisible();

        // Find and click the first "Se svar" button
        page.locator("//vaadin-button[contains(text(),'Se svar')]").first().click();

        // Verify the dialog opens with the header
        assertThat(page.getByText("Søvnundersøgelse Svar - 9:54")).isVisible();

        // Verify the dialog content is displayed
        assertThat(page.getByText("Survey details for 9:54 will be displayed here.")).isVisible();

        // Click the close button
        page.locator("//vaadin-button[contains(text(),'Luk')]").click();

        browser.close();
    }

    @Test
    public void testDateFilter() {
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        page.navigate("http://localhost:" + port + "/sleep-stats");

        // Verify the filter button is visible
        assertThat(page.getByText("Filtrer")).isVisible();

        // Click the filter button
        page.locator("//vaadin-button[contains(text(),'Filtrer')]").click();

        // Page should still be visible (filter applied successfully)
        assertThat(page.getByText("Søvnstatistik")).isVisible();

        browser.close();
    }

    @Test
    public void testStatCardsVisible() {
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        page.navigate("http://localhost:" + port + "/sleep-stats");

        // Verify all stat cards are visible
        assertThat(page.getByText("TIB - Tid i seng")).isVisible();
        assertThat(page.getByText("TST - Total Søvntid")).isVisible();
        assertThat(page.getByText("søvneffektivitet")).isVisible();
        assertThat(page.getByText("SOL - Indsovningstid")).isVisible();
        assertThat(page.getByText("WASO - Opvågninger")).isVisible();
        assertThat(page.getByText("Morgenfølelse")).isVisible();

        browser.close();
    }
        */
}
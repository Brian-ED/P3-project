package com.example.application;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

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

   // Helper method to log in
private void login(Page page, String username, String password) {
    page.navigate("http://localhost:" + port + "/login");
    
    // Wait for login form to load
    page.waitForSelector("vaadin-login-form", new Page.WaitForSelectorOptions().setTimeout(10000));
    
    // Wait for the form to be fully rendered
    page.waitForTimeout(500);
    
    page.keyboard().type(username);
    // Tab to password field
    page.keyboard().press("Tab");
    page.keyboard().type(password);
    
    // Submit (Enter or Tab to button then Enter)
    page.keyboard().press("Enter");
    
    // Wait for navigation after login
    page.waitForTimeout(1000);
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
    
    @Test
public void testLoginFormFields() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        page.navigate("http://localhost:" + port + "/login");
        
        // Wait for the login form to load
        page.waitForSelector("vaadin-login-form", new Page.WaitForSelectorOptions().setTimeout(10000));
        
          // The vaadin-login-form component itself is visible
        assertThat(page.locator("vaadin-login-form")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}

@Test
public void testLoginError() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Navigate to login page with error parameter
        page.navigate("http://localhost:" + port + "/login?error");
        
        // Wait for the login form to load
        page.waitForSelector("vaadin-login-form", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify error message is displayed
        assertThat(page.locator("vaadin-login-form[error]")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}


@Test
public void testSurveyAnswerDialog() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "admin", "admin");

        // Navigate to sleep stats page
        page.navigate("http://localhost:" + port + "/sleep-stats");
        
        // Wait for the page content to load
        page.waitForSelector("text=Søvnundersøgelse Svar", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify the page title/heading is visible
        assertThat(page.getByText("Søvnundersøgelse Svar")).isVisible();

        // Find and click the first "Se svar" button
        page.locator("vaadin-button:has-text('Se svar')").first().click();

        // Wait for dialog to appear
        page.waitForSelector("text=Søvnundersøgelse Svar - 9:54", new Page.WaitForSelectorOptions().setTimeout(5000));

        // Verify the dialog opens with the header
        assertThat(page.getByText("Søvnundersøgelse Svar - 9:54")).isVisible();

        // Verify the dialog content is displayed
        assertThat(page.getByText("Survey details for 9:54 will be displayed here.")).isVisible();

        // Click the close button
        page.locator("vaadin-button:has-text('Luk')").click();
        
    } finally {
        page.close();
        context.close();
    }
}

@Test
public void testDateFilter() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "admin", "admin");

        page.navigate("http://localhost:" + port + "/sleep-stats");
        
        // Wait for the filter button to be visible
        page.waitForSelector("text=Filtrer", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify the filter button is visible
        assertThat(page.getByText("Filtrer")).isVisible();

        // Click the filter button
        page.locator("vaadin-button:has-text('Filtrer')").click();

        // Wait a moment for filter to be applied
        page.waitForTimeout(1000);

      // Be more specific - target the H1 heading instead of any text
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Søvnstatistik"))).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}

@Test
public void testStatCardsVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "admin", "admin");
        
        page.navigate("http://localhost:" + port + "/sleep-stats");
        
        // Wait for the first stat card to load
        page.waitForSelector("text=TIB - Tid i seng", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify all stat cards are visible
        assertThat(page.getByText("TIB - Tid i seng")).isVisible();
        assertThat(page.getByText("TST - Total Søvntid")).isVisible();
        assertThat(page.getByText("søvneffektivitet")).isVisible();
        assertThat(page.getByText("SOL - Indsovningstid")).isVisible();
        assertThat(page.getByText("WASO - Opvågninger")).isVisible();
        assertThat(page.getByText("Morgenfølelse")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}
}
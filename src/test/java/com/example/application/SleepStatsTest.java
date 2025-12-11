package com.example.application;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(
    classes = Application.class, 
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Import(TestSecurityConfig.class)  
@Tag("playwright")
public class SleepStatsTest {

    @LocalServerPort
    private int port;

    static Playwright playwright = Playwright.create();

    @Test
    public void testLoginPageLoads() {
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        page.navigate("http://localhost:" + port + "/login");

        // Verify the page title is "Login"
        assertThat(page).hasTitle("Login");

        // Verify the login form is visible
        assertThat(page.locator("vaadin-login-form")).isVisible();

        browser.close();
    }

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
}
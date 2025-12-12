package com.example.application;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class PlaywrightSleepStatsTest extends TestHelperFunctions {

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
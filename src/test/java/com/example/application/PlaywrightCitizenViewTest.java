package com.example.application;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;



public class PlaywrightCitizenViewTest extends TestHelperFunctions {

    @Test
    public void testCitizenViewHeaderVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "citizen", "citizen");
        
        page.navigate("http://localhost:" + port + "/citizen");
        
        // Wait for welcome message to load
        page.waitForSelector("text=Velkommen,", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify header is visible
        assertThat(page.locator("h2:has-text('Velkommen,')")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}
@Test
public void testMorningAndEveningCardsVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "citizen", "citizen");
        
        page.navigate("http://localhost:" + port + "/citizen");
        
        // Wait for morning card to load
        page.waitForSelector("text=Morgensvar", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify morning card elements are visible
        assertThat(page.getByText("Morgensvar").first()).isVisible();
        assertThat(page.getByText("Udfyld dit morgenskema om nattens søvn")).isVisible();
        
        // Verify evening card elements are visible
        assertThat(page.getByText("Aftensvar").first()).isVisible();
        assertThat(page.getByText("Udfyld dit aftensskema om dagens aktiviteter")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}
@Test
public void testFillSurveyButtonsVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "citizen", "citizen");
        
        page.navigate("http://localhost:" + port + "/citizen");
        
        // Wait for page to load
        page.waitForSelector("text=Morgensvar", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify "Udfyld aftensvar" buttons are visible (There are two for some reason? test just checks that there is one)
        assertThat(page.getByText("Udfyld aftensvar").first()).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}

@Test
public void testHistoryButtonVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "citizen", "citizen");
        
        page.navigate("http://localhost:" + port + "/citizen");
        
        // Wait for history section to load
        page.waitForSelector("text=Historik", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify history button is visible
        assertThat(page.getByText("Historik")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}

@Test
public void testLatestEntriesCardVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "citizen", "citizen");
        
        page.navigate("http://localhost:" + port + "/citizen");
        
        // Wait for latest entries card to load
        page.waitForSelector("text=Seneste indtastninger", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify latest entries card elements are visible
        assertThat(page.getByText("Seneste indtastninger")).isVisible();
        assertThat(page.getByText("Oversigt over dine seneste søvnregistreringer:")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}
}
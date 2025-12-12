package com.example.application;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class PlaywrightDashboardViewTest extends TestHelperFunctions {

    @Test
    public void testDashboardStatsCardsVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "admin", "admin");
        
        page.navigate("http://localhost:" + port + "/dashboard");
        
        // Wait for the first stat card to load
        page.waitForSelector("text=Totale borgere", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify all stat cards are visible
        assertThat(page.getByText("Totale borgere")).isVisible();
        assertThat(page.getByText("Registrerede brugere")).isVisible();
        assertThat(page.getByText("Mine borgere").first()).isVisible();
        assertThat(page.getByText("Tildelte borgere")).isVisible();
        assertThat(page.getByText("Aktive i dag")).isVisible();
        assertThat(page.getByText("Nye indtastninger")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}

@Test
public void testDashboardHeaderVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "admin", "admin");
        
        page.navigate("http://localhost:" + port + "/dashboard");
        
        // Wait for welcome message to load
        page.waitForSelector("text=Velkommen, John Doe", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify header elements are visible
        assertThat(page.getByText("Velkommen, John Doe")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}

@Test
public void testCitizenOverviewSectionVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "admin", "admin");
        
        page.navigate("http://localhost:" + port + "/dashboard");
        
        // Wait for section title to load
        page.waitForSelector("text=Borgersoversigt", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify section elements are visible
        assertThat(page.getByText("Borgersoversigt")).isVisible();
        assertThat(page.getByText("Administrer og overvåg borgernes søvndata")).isVisible();
        assertThat(page.getByPlaceholder("Søg efter borgernavn...").first()).isVisible();
        assertThat(page.getByText("Vis kun mine borgere")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}



@Test
public void testSortComboBoxVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "admin", "admin");
        
        page.navigate("http://localhost:" + port + "/dashboard");
        
        // Wait for sort combobox to load
        page.waitForSelector("input[placeholder='Sorter efter']", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify sort combobox is visible
        assertThat(page.locator("input[placeholder='Sorter efter']")).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}

@Test
public void testViewDataButtonsVisible() {
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    try {
        // Login first
        login(page, "admin", "admin");
        
        page.navigate("http://localhost:" + port + "/dashboard");
        
        // Wait for page to load
        page.waitForSelector("text=Borgersoversigt", new Page.WaitForSelectorOptions().setTimeout(10000));

        // Verify "Se data" buttons are visible (there should be at least one)
        assertThat(page.getByText("Se data").first()).isVisible();
        
    } finally {
        page.close();
        context.close();
    }
}


}
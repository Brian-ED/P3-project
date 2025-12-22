package com.example.application;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class PlaywrightLoginViewTest extends TestHelperFunctions {
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

 
}
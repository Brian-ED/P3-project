package com.example.app_name_here;

// New imports for new example
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 */
@SpringBootApplication
@Theme(value = "my-app")
public class Application implements AppShellConfigurator { // I don't understand why "implements AppShellConfigurator" was added
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

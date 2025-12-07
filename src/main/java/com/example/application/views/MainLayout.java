package com.example.application.views;

import java.util.List;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@PermitAll
@AnonymousAllowed
public class MainLayout extends AppLayout implements AfterNavigationObserver {
    
    private final AuthenticationContext authenticationContext;
    private H1 viewTitle;

    public MainLayout(AuthenticationContext authenticationContext) {
        setPrimarySection(Section.DRAWER);
        addHeaderContent();
        this.authenticationContext = authenticationContext;

        UI ui = UI.getCurrent();

// Light mode defaults
ui.getElement().getStyle().set("--lumo-base-color", "#f7f7f7");
ui.getElement().getStyle().set("--lumo-primary-color", "#0467e7");
ui.getElement().getStyle().set("--lumo-base2-color", "white");
ui.getElement().getStyle().set("--lumo-body-text-color", "#0f172a");
ui.getElement().getStyle().set("--lumo-secondary-text-color", "#64748b");
ui.getElement().getStyle().set("--lumo-moderate-color", "#fffbeb");
    }

    private void addHeaderContent(){
        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE, LumoUtility.Margin.Left.XLARGE);
        
        Button logout = createLogout();

        Button toggleTheme = modeDarkLight();

        HorizontalLayout headerLayout = new HorizontalLayout();
            headerLayout.setWidthFull();
            headerLayout.setAlignItems(Alignment.CENTER);
            headerLayout.add(viewTitle, toggleTheme, logout);
            headerLayout.expand(viewTitle);

            addToNavbar(headerLayout);
    }

    private Button createLogout() {
                var logout = new Button("Logout", VaadinIcon.POWER_OFF.create());
                logout.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                logout.addClickListener(e -> authenticationContext.logout()); 
            logout.getElement().getStyle().set("background", "darkblue");
            logout.getElement().getStyle().set("color", "white");
            logout.getElement().getStyle().set("border-radius", "8px");
            logout.getElement().getStyle().set("padding", "8px 14px");
            logout.getElement().getStyle().set("margin-right", "30px");
            
            return logout;
    }

private Button modeDarkLight() {
    UI ui = UI.getCurrent();
    boolean isDark = ui.getElement().getThemeList().contains(Lumo.DARK);

    // Create toggle button
    Button toggleTheme = new Button(isDark ? "☀ Light Mode" : "🌙 Dark Mode");

    toggleTheme.addClickListener(event -> {
        boolean currentlyDark = ui.getElement().getThemeList().contains(Lumo.DARK);

        // Clear all themes first
        ui.getElement().getThemeList().clear();

        if (!currentlyDark) {
            // Switch to DARK
            ui.getElement().getThemeList().add(Lumo.DARK);
            removeLightModeInlineStyles(ui);  // Remove inline light styles
            enableAmoledBlack(ui);            // Apply custom dark colors
        } else {
            // Switch to LIGHT
            ui.getElement().getThemeList().add(Lumo.LIGHT);
            setCustomLightMode(ui);           // Reapply your custom light colors
        }

        // Update button label
        toggleTheme.setText(currentlyDark ? "🌙 Dark Mode" : "☀ Light Mode");
    });

    toggleTheme.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    toggleTheme.getStyle().set("margin-right", "20px");

    return toggleTheme;
}

private void removeLightModeInlineStyles(UI ui) {
    ui.getElement().getStyle().remove("--lumo-base-color");
    ui.getElement().getStyle().remove("--lumo-base2-color");
    ui.getElement().getStyle().remove("--lumo-body-text-color");
    ui.getElement().getStyle().remove("--lumo-secondary-text-color");
    ui.getElement().getStyle().remove("--lumo-moderate-color");
}

// Reapply your custom light mode colors
private void setCustomLightMode(UI ui) {
    ui.getElement().getStyle().set("--lumo-base-color", "#f7f7f7");
    ui.getElement().getStyle().set("--lumo-base2-color", "white");
    ui.getElement().getStyle().set("--lumo-body-text-color", "#0f172a");
    ui.getElement().getStyle().set("--lumo-secondary-text-color", "#64748b");
    ui.getElement().getStyle().set("--lumo-moderate-color", "#fffbeb");
}

private void enableAmoledBlack(UI ui) {
    ui.getElement().executeJs(
        """
        const style = document.createElement('style');
        style.setAttribute('amoled', '');
        style.innerHTML = `

        [theme~="dark"] {
            --lumo-base-color: #000;
            --lumo-base2-color: #000;
            --lumo-shade: #000;
            --lumo-shade-90pct: #050505;

            --lumo-body-text-color: #fff;
            --lumo-primary-text-color: #fff;
            --lumo-secondary-text-color: #ddd;
            --lumo-tertiary-text-color: #bbb;

            --lumo-header-text-color: #fff;
            --lumo-disabled-text-color: #777;
            --lumo-link-color: #fff;

            --lumo-contrast-5pct: #050505;
            --lumo-contrast-10pct: #0a0a0a;
            --lumo-contrast-20pct: #111;
            --lumo-contrast-30pct: #1a1a1a;

            --lumo-primary-color: #fff;
            --lumo-primary-color-50pct: rgba(255,255,255,.5);
            --lumo-primary-color-10pct: rgba(255,255,255,.1);

            --lumo-moderate-color": "#ff7b00ff");
        }
    
        /* Force ALL text white */
        [theme~="dark"] * {
            color: white !important;
            caret-color: white;
        }

        /* Borders & outline cleanup */
        [theme~="dark"] input,
        [theme~="dark"] textarea,
        [theme~="dark"] select {
            border-color: #333 !important;
            background-color: #000 !important;
        }

        /* Icons */
        [theme~="dark"] vaadin-icon {
            fill: white;
        }

        /* Buttons */
        [theme~="dark"] vaadin-button {
            background-color: #000 !important;
            color: white !important;
            border: 1px solid #222;
        }

        /* Menu / Nav */
        [theme~="dark"] vaadin-app-layout,
        [theme~="dark"] vaadin-side-nav,
        [theme~="dark"] vaadin-drawer-toggle {
            background-color: #000 !important;
        }

        `;
        document.head.appendChild(style);
        """
    );
}




    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}

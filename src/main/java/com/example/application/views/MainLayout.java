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
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

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
        }

    private void addHeaderContent(){
        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE, LumoUtility.Margin.Left.XLARGE);
        
        Button logout = createLogout();

        HorizontalLayout headerLayout = new HorizontalLayout();
            headerLayout.setWidthFull();
            headerLayout.setAlignItems(Alignment.CENTER);
            headerLayout.add(viewTitle, logout);
            headerLayout.expand(viewTitle);

            addToNavbar(headerLayout);
    }

    private Button createLogout() {
                var logout = new Button("Logout", VaadinIcon.POWER_OFF.create());
                logout.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                logout.addClickListener(e -> authenticationContext.logout()); 
            logout.getElement().getStyle().set("background", "#2219c3ff");
            logout.getElement().getStyle().set("color", "white");
            logout.getElement().getStyle().set("border-radius", "8px");
            logout.getElement().getStyle().set("padding", "8px 14px");
            logout.getElement().getStyle().set("margin-right", "30px");
            
            return logout;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}

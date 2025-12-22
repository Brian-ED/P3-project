package com.example.application.views;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.application.security.ui.view.LoginView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
public class root extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                event.forwardTo(DashboardView.class);
            } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CITIZEN"))) {
                event.forwardTo(CitizenView.class);
            } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADVISOR"))) {
                event.forwardTo(DashboardView.class);
            } else {
                event.forwardTo(LoginView.class);
            }
        } else {
            event.forwardTo(LoginView.class);
        }
    }
}

package com.example.application.views;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class Main extends VerticalLayout {
    public Main() {
        LoginForm loginForm = new LoginForm();
        add(loginForm);
    }
}

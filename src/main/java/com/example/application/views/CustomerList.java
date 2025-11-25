package com.example.application.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route("customer/list")
@PermitAll
@PageTitle("Hellooo")
public class CustomerList extends Div {
    public CustomerList() {
        setText("Customers!");
    }
}

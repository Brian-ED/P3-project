package com.example.application.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("customer/list")
@PageTitle("Hellooo")
public class CustomerList extends Div {
    public CustomerList() {
        setText("Customers!");
    }
}

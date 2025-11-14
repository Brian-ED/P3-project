package com.example.component.formLayout;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("form-layout-expand-columns")
public class FormLayoutExpandColumns extends Div {

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    PasswordField password = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm password");

    FormLayout formLayout = new FormLayout();


    public FormLayoutExpandColumns() {
      formLayout.setAutoResponsive(true);
      formLayout.setColumnWidth("8em");
      formLayout.setExpandColumns(true);
      formLayout.addFormRow(firstName, lastName);
      formLayout.addFormRow(email);
      formLayout.addFormRow(password, confirmPassword);


      TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm password");

        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.setColumnWidth("8em");
        formLayout.setExpandColumns(true);
        formLayout.addFormRow(firstName, lastName);
        formLayout.addFormRow(email);
        formLayout.addFormRow(password, confirmPassword);
        formLayout.setWidthFull();

        SplitLayout splitLayout = new SplitLayout(formLayout, new Div());
        add(splitLayout);
    }

}

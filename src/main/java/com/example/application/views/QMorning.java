package com.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("morning")
public class QMorning extends VerticalLayout {
  TextField firstName = new TextField("First name");
  TextField lastName = new TextField("Last name");
  EmailField email = new EmailField("Email address");
  PasswordField password = new PasswordField("Password");
  PasswordField confirmPassword = new PasswordField("Confirm password");
  Button next = new Button("Næste >");
  Button prev = new Button("< Tilbage");

  RadioButtonGroup<String> TakesMeds = new RadioButtonGroup<>();
  FormLayout formLayout = new FormLayout();

  public QMorning() {
    // Setup the text on the buttons
    TakesMeds.setLabel("Tog du sovemedicin eller melatonin piller?");
    TakesMeds.setItems("Ja", "Nej");

    // Make each question be on their own row
    TakesMeds.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

    // Create a label to display the selected value
    Span selected = new Span("Du har ikke svaret");

    // Add a listener for selection changes
    TakesMeds.addValueChangeListener(event -> {
      String value = event.getValue();
      if (value != null) {
        selected.setText("You chose: " + value);
      } else {
        selected.setText("No selection yet");
      }
    });

    formLayout.addFormRow(firstName, lastName);
    formLayout.addFormRow(email);
    formLayout.addFormRow(password, confirmPassword);
    formLayout.addFormRow(TakesMeds);
    formLayout.addFormRow(selected);
    formLayout.addFormRow(prev, next);
    add(formLayout);
  }
}

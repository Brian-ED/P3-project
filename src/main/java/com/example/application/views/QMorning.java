package com.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;

@Route("morning")
public class QMorning extends VerticalLayout {
  Button next = new Button("Næste >");
  Button prev = new Button("< Tilbage");

  RadioButtonGroup<String> TakesMeds = new RadioButtonGroup<>();
  FormLayout formLayout = new FormLayout();
  H3 h3 = new H3();
  VerticalLayout layout = new VerticalLayout();
  HorizontalLayout buttonLayout = new HorizontalLayout();
  
  public QMorning() {
    // Set question
    h3.add("Tog du sovemedicin eller melatonin piller?");
//
//    // Radio buttons
//    TakesMeds.setItems("Ja", "Nej");
//
//    // Make each question be on their own row
//    TakesMeds.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
//
//    // Create a label to display the selected value
//    Span selected = new Span("Du har ikke svaret");
//
//    // Add a listener for selection changes
//    TakesMeds.addValueChangeListener(event -> {
//      String value = event.getValue();
//      if (value != null) {
//        selected.setText("You chose: " + value);
//      } else {
//        selected.setText("No selection yet");
//      }
//    });
    


    VerticalLayout layout = new VerticalLayout();
layout.setAlignItems(FlexComponent.Alignment.CENTER);

Div h3 = new Div("h3");
h3.setClassName("id-h3"); 
layout.add(h3);

layout.setPadding(true);

Button prev = new Button("Før");
Button next = new Button("Næste");

// Put buttons in a horizontal layout
HorizontalLayout buttonRow = new HorizontalLayout(prev, next);
buttonRow.setWidthFull(); 
buttonRow.setJustifyContentMode(JustifyContentMode.BETWEEN); // Align left + right

layout.add(buttonRow);

add(layout);


//    formLayout.addFormRow(layout);
//    formLayout.addFormRow(TakesMeds);
//    formLayout.addFormRow(selected);
//    buttonLayout.addToMiddle(prev);
//    buttonLayout.addToEnd(next);
//    add(buttonLayout);
  }
}

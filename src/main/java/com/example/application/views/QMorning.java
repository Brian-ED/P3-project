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

    setAlignItems(FlexComponent.Alignment.CENTER);
    setPadding(true);

    // Put buttons in a horizontal layout
    HorizontalLayout buttons = new HorizontalLayout(prev, next);
    buttons.setWidthFull(); 
    buttons.setJustifyContentMode(JustifyContentMode.BETWEEN); 


    setHeightFull();
    setJustifyContentMode(JustifyContentMode.BETWEEN);
     // Align left + right
    add(h3,buttons);


    next.setWidth("220px");
    next.setHeight("110px");
    prev.setWidth("220px");
    prev.setHeight("110px");
    prev.getStyle().set("background-color", "#262ecaff").set("color", "white");
    next.getStyle().set("background-color", "#262ecaff").set("color", "white");
    prev.getStyle().set("font-size", "30px");
    next.getStyle().set("font-size", "30px");
//    formLayout.addFormRow(layout);
//    formLayout.addFormRow(TakesMeds);
//    formLayout.addFormRow(selected);
//    buttonLayout.addToMiddle(prev);
//    buttonLayout.addToEnd(next);
//    add(buttonLayout);
  }
}

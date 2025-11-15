package com.example.application.views;

import com.example.application.AllQ;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.Route;

@Route("morning3")
public class QMorning3 extends VerticalLayout {
  private static final Object[] QUESTIONS = null;
  Button next = new Button("Næste >");
  Button prev = new Button("< Tilbage");

  RadioButtonGroup<String> ActionsBeforeSleep = new RadioButtonGroup<>();
  H3 h3 = new H3();
  VerticalLayout layout = new VerticalLayout();
  HorizontalLayout buttonLayout = new HorizontalLayout();
  
  public QMorning3() {
    setAlignItems(FlexComponent.Alignment.CENTER);
    layout.setAlignItems(FlexComponent.Alignment.START);
    layout.setJustifyContentMode(JustifyContentMode.START);

    // Set question
    layout.add(new H3(AllQ.QUESTIONS[3]));
//    setAlignItems(FlexComponent.Alignment.CENTER);

    // Put buttons in a horizontal layout
    HorizontalLayout buttons = new HorizontalLayout(prev, next);
    buttons.setWidthFull();
    buttons.setJustifyContentMode(JustifyContentMode.BETWEEN);

    setHeightFull();
    setJustifyContentMode(JustifyContentMode.BETWEEN);

    // Setup the text on the buttons
    ActionsBeforeSleep.setItems();

    // Make each question be on their own row
    ActionsBeforeSleep.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

//    // Create a label to display the selected value
//    Span selected = new Span("Du har ikke svaret");
//    // Add a listener for selection changes
//    TakesMeds.addValueChangeListener(event -> {
//      String value = event.getValue();
//      if (value != null) {
//        selected.setText("Du valgte: " + value);
//      } else {
//        selected.setText("Intet valgt");
//      }
//    });

    layout.add(h3, ActionsBeforeSleep);
    layout.setAlignSelf(Alignment.START, h3);
    layout.setAlignSelf(Alignment.START, ActionsBeforeSleep);
    add(layout, buttons);

    next.setWidth("220px");
    next.setHeight("110px");
    prev.setWidth("220px");
    prev.setHeight("110px");
    prev.getStyle().set("background-color", "#262ecaff").set("color", "white");
    next.getStyle().set("background-color", "#262ecaff").set("color", "white");
    prev.getStyle().set("font-size", "30px");
    next.getStyle().set("font-size", "30px");
  }
}


package com.example.application.views;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

class StatisticBox extends VerticalLayout {
  H3 title;
  H3 valueField;

  StatisticBox (String titleText) {
    getStyle()
      .set("border-radius", "30px")
      .set("border-style", "solid")
      .set("border-color", "darkgray")
      .set("border-width", "2px");
    this.title = new H3(titleText);
    this.valueField = new H3("0");
    add(title, valueField);
  }

  public void set(Number value) {
    valueField.setText(value.toString());
  }
}

@Route("sleepadvisor")
@RolesAllowed({"ADVISOR", "ADMIN"})
public class SleepAdvisor extends VerticalLayout {
  public SleepAdvisor() {
    setHeightFull();
    setJustifyContentMode(JustifyContentMode.BETWEEN);
    setAlignItems(FlexComponent.Alignment.CENTER);

    StatisticBox totalCitizens = new StatisticBox("Totale borgere");
    StatisticBox myCitizens = new StatisticBox("Mine borgere");
    StatisticBox activeToday = new StatisticBox("Aktive i dag");
    totalCitizens.set(6);
    totalCitizens.set(3);

    HorizontalLayout twoWords = new HorizontalLayout(
      totalCitizens, myCitizens, activeToday
    );
    twoWords.setWidthFull();
    twoWords.setJustifyContentMode(JustifyContentMode.BETWEEN);

    VerticalLayout layout = new VerticalLayout(twoWords);
    layout.setAlignItems(FlexComponent.Alignment.START);
    layout.setJustifyContentMode(JustifyContentMode.START);

    add(layout);
  }

}

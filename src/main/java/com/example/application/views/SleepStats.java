package com.example.application.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;



@JsModule("./SleepStats.js")
@JavaScript("https://cdn.jsdelivr.net/npm/chart.js")
@Route(value = "sleep-stats", layout = MainLayout.class)
@PageTitle("Søvnstatistik")
@PermitAll
public class SleepStats extends VerticalLayout {

    private final Grid<SleepEntry> grid = new Grid<>(SleepEntry.class, false);
    private final List<SleepEntry> entries = new ArrayList<>();

    public SleepStats() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getElement().getStyle().set("background-color", "#f7f7f7ff");

        HorizontalLayout top = new HorizontalLayout();
        top.setWidthFull();
        top.getStyle().set("align-items", "center");

        // Breadcrumbs
        HorizontalLayout crumbs = new HorizontalLayout();
        crumbs.getStyle().set("align-items", "center");

      
        RouterLink dashboard = new RouterLink("Dashboard", DashboardView.class);
     ;
        Span sep2 = new Span("/");
        Span current = new Span("Søvnstatistik");
        current.getStyle().set("font-weight", "600");

        crumbs.add(dashboard, sep2, current);

   

 Button logout = new Button("Logout");
        logout.getElement().getStyle().set("background", "#2219c3ff");
        logout.getElement().getStyle().set("color", "white");
        logout.getElement().getStyle().set("border-radius", "8px");
        logout.getElement().getStyle().set("padding", "8px 14px");



        top.add(crumbs, logout);
        top.expand(crumbs);

        add(top);

        // Controls row with date range filter
HorizontalLayout controls = new HorizontalLayout();
controls.setWidthFull();
controls.setAlignItems(Alignment.END);

DatePicker startDate = new DatePicker("Fra dato");
startDate.setValue(LocalDate.now().minusDays(7)); // Default: last 7 days
startDate.setWidth("200px");

DatePicker endDate = new DatePicker("Til dato");
endDate.setValue(LocalDate.now()); // Default: today
endDate.setWidth("200px");

Button filterButton = new Button("Filtrer", e -> {
    applyDateFilter(startDate.getValue(), endDate.getValue());
});
controls.add(startDate, endDate, filterButton);
add(controls);


        // Stats cards
        HorizontalLayout statsRow = new HorizontalLayout();
        statsRow.setWidthFull();
        statsRow.setSpacing(true);
        statsRow.add(createStatCard("TIB - Tid i seng", " 9t 15m"),
                     createStatCard("TST - Total Søvntid", " 8t 1m"),
                     createStatCard("søvneffektivitet", " 90%"),
                     createStatCard("SOL - Indsovningstid", " 15m"),
                     createStatCard("WASO - Opvågninger" , " 40m"),
                     createStatCard("Morgenfølelse", " 4.0/5")
                    );
                     
        add(statsRow);


        

// Create a wrapper for centering the sleep chart
Div sleepChartWrapper = new Div();
sleepChartWrapper.setWidthFull();
sleepChartWrapper.getStyle()
    .set("display", "flex")
    .set("justify-content", "center");

// Create the sleep chart container
Div chartContainer = new Div();
chartContainer.setId("sleepChartContainer");
chartContainer.setWidth("90%"); // Adjust this percentage (e.g., 80%, 85%, 90%)
chartContainer.setHeight("400px");
chartContainer.getStyle()
    .set("background-color", "white")
    .set("border-radius", "12px")
    .set("border", "1px solid #e0e0e0")
    .set("padding", "20px")
    .set("box-shadow", "0 2px 4px rgba(0,0,0,0.05)");

sleepChartWrapper.add(chartContainer);
add(sleepChartWrapper);
        
        addAttachListener(event -> {
    getUI().ifPresent(ui -> {
        ui.getPage().executeJs(
            "console.log('Attempting to call createSleepChart...'); " +
            "console.log('window.createSleepChart exists?', typeof window.createSleepChart); " +
            "if (window.createSleepChart) { " +
            "  window.createSleepChart($0); " +
            "} else { " +
            "  console.error('createSleepChart not found on window'); " +
            "}",
            "sleepChartContainer"
        );
    });
});
         



// Create a wrapper for centering the effectiveness chart
Div chartWrapper = new Div();
chartWrapper.setWidthFull();
chartWrapper.getStyle()
    .set("display", "flex")
    .set("justify-content", "center")
    .set("margin-top", "20px"); // Add spacing between charts

// Create the effectiveness chart container
Div effectivenessChartContainer = new Div();
effectivenessChartContainer.setId("effectivenessChartContainer");
effectivenessChartContainer.setWidth("90%"); // Adjust this percentage (e.g., 80%, 85%, 90%)
effectivenessChartContainer.setHeight("400px");
effectivenessChartContainer.getStyle()
    .set("background-color", "white")
    .set("border-radius", "12px")
    .set("border", "1px solid #e0e0e0")
    .set("padding", "20px")
    .set("box-shadow", "0 2px 4px rgba(0,0,0,0.05)");

chartWrapper.add(effectivenessChartContainer);
add(chartWrapper);

// Call the chart on attach
addAttachListener(event -> {
    getUI().ifPresent(ui -> {
        ui.getPage().executeJs(
            "console.log('Attempting to call createEffectivenessChart...'); " +
            "console.log('window.createEffectivenessChart exists?', typeof window.createEffectivenessChart); " +
            "if (window.createEffectivenessChart) { " +
            "  window.createEffectivenessChart($0); " +
            "} else { " +
            "  console.error('createEffectivenessChart not found on window'); " +
            "}",
            "effectivenessChartContainer"
        );
    });
});

refreshGrid();
    }
    private void applyDateFilter(LocalDate start, LocalDate end) {
    if (start == null || end == null) {
        refreshGrid();
        return;
    }
    
    if (start.isAfter(end)) {
        // Optionally show an error notification
        refreshGrid();
        return;
    }
    
    List<SleepEntry> filtered = new ArrayList<>();
    for (SleepEntry entry : entries) {
        LocalDate entryDate = entry.getDate();
        if (entryDate != null && 
            !entryDate.isBefore(start) && 
            !entryDate.isAfter(end)) {
            filtered.add(entry);
        }
    }
    grid.setItems(filtered);
}

    private void refreshGrid() {
        grid.setItems(entries);
    }

    private Div createStatCard(String title, String value) {
        Div card = new Div();
        card.getStyle().set("background", "white");
        card.getStyle().set("border-radius", "12px");
        card.getStyle().set("padding", "16px");
        card.getStyle().set("width", "240px");
        card.getStyle().set("box-shadow", "0 1px 6px rgba(15,23,42,0.04)");

        Span t = new Span(title);
        t.getStyle().set("color", "#64748b");
        t.getStyle().set("font-size", "13px");

        Span v = new Span(value);
        v.getStyle().set("font-size", "26px");
        v.getStyle().set("font-weight", "700");

        card.add(t, v);
        return card;
    }

    

    public static class SleepEntry {
        private LocalDate date;
        private double duration;
        private String quality;
        private String comment;

        public SleepEntry() {}

        public SleepEntry(LocalDate date, double duration, String quality, String comment) {
            this.date = date;
            this.duration = duration;
            this.quality = quality;
            this.comment = comment;
        }

        public LocalDate getDate() { return date; }
        public double getDuration() { return duration; }
        public String getQuality() { return quality; }
        public String getComment() { return comment; }

        public void setDate(LocalDate date) { this.date = date; }
        public void setDuration(double duration) { this.duration = duration; }
        public void setQuality(String quality) { this.quality = quality; }
        public void setComment(String comment) { this.comment = comment; }
    }
}

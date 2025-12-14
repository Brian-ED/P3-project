package com.example.application.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.application.database.PostgreSQLDatabaseControler;
import com.example.application.model.Citizen;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.datepicker.DatePicker;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;



@JsModule("./SleepStats.js")
@JavaScript("https://cdn.jsdelivr.net/npm/chart.js")
@Route(value = "sleep-stats/:citizenId", layout = MainLayout.class)
@PageTitle("Søvnstatistik")
@RolesAllowed({"ADVISOR", "ADMIN"})
@PermitAll
public class SleepStats extends VerticalLayout implements BeforeEnterObserver{
    private Long citizenId;
    private Citizen currentCitizen;
    private final PostgreSQLDatabaseControler db; // inject via constructor

    private final Grid<SleepEntry> grid = new Grid<>(SleepEntry.class, false);
    private final List<SleepEntry> entries = new ArrayList<>();
    public SleepStats(PostgreSQLDatabaseControler db) {
        this.db = db;
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getElement().getStyle().set("background-color", "#f7f7f7ff");

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getElement().getStyle().set("background-color", "#f7f7f7ff");

        HorizontalLayout top = new HorizontalLayout();
        top.setWidthFull();
        top.setAlignItems(Alignment.CENTER);

        // Breadcrumbs
        HorizontalLayout crumbs = new HorizontalLayout();
        RouterLink dashboard = new RouterLink("Dashboard", DashboardView.class);
        Span sep2 = new Span("/");
        Span current = new Span("Søvnstatistik");
        current.getStyle().set("font-weight", "600");
        crumbs.add(dashboard, sep2, current);

        top.add(crumbs);
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
        statsRow.add(createStatCard("TIB - Tid i seng", " "+ "9t 15m"),
                     createStatCard("TST - Total Søvntid", " "+ "8t 1m"),
                     createStatCard("søvneffektivitet", " "+ (Math.round((0.9)*100))+ "%"),
                     createStatCard("SOL - Indsovningstid", " "+ "15m"),
                     createStatCard("WASO - Opvågninger" , " "+ "40m"),
                     createStatCard("Morgenfølelse", " "+ "4.0"+"/5")
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
        effectivenessChartContainer.setWidth("90%");
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
            getUI().ifPresent(ui -> ui.getPage().executeJs(
                "if (window.createEffectivenessChart) { window.createEffectivenessChart($0); }",
                "effectivenessChartContainer"
            ));
        });

        // Create a wrapper for centering the survey answers box
        Div surveyWrapper = new Div();
        surveyWrapper.setWidthFull();
        surveyWrapper.getStyle()
            .set("display", "flex")
            .set("justify-content", "center")
            .set("margin-top", "20px");

        // Sleep Survey Answers Box
        Div surveyBox = createSurveyAnswersBox();
        surveyWrapper.add(surveyBox);
        add(surveyWrapper);

        refreshGrid();
    }

        public void beforeEnter(BeforeEnterEvent event) {
        String idParam = event.getRouteParameters().get("citizenId").orElse(null);
        if (idParam != null) {
            try {
                citizenId = Long.valueOf(idParam);
                currentCitizen = db.getCitizenById(citizenId).orElse(null);
                if (currentCitizen != null) {
                    // Update UI for this citizen
                    loadCitizenData(currentCitizen);
                } else {
                    // Optionally show a notification: citizen not found
                }
            } catch (NumberFormatException e) {
                // Handle invalid ID format
            }
        }
    }

        private void loadCitizenData(Citizen citizen) {
            entries.clear(); entries.addAll(db.getSleepEntriesForCitizen(citizen));
            refreshGrid();
        }
     private Div createSurveyAnswersBox() {
        Div box = new Div();
        box.setWidth("90%");
        box.setHeight("400px");
        box.getStyle()
            .set("background", "white")
            .set("border-radius", "12px")
            .set("border", "1px solid #e0e0e0")
            .set("padding", "20px")
            .set("box-shadow", "0 2px 4px rgba(0,0,0,0.05)")
            .set("overflow-y", "auto");

        // Title
        Span title = new Span("Søvnundersøgelse Svar");
        title.getStyle()
            .set("font-size", "18px")
            .set("font-weight", "600")
            .set("display", "block")
            .set("margin-bottom", "16px");
        box.add(title);

        // Create table structure
        Div table = new Div();
        table.getStyle()
            .set("display", "grid")
            .set("grid-template-columns", "150px 200px 200px")
            .set("gap", "12px 20px")
            .set("font-size", "14px");

        // Header row
        table.add(createTableHeader("Dato"));
        table.add(createTableHeader("Morgensvar"));
        table.add(createTableHeader("Aftensvar"));

        // Sample data rows
        table.add(createTableCell("19/10/2025"));
        table.add(createTableCellWithButton("9:54"));
        table.add(createTableCellWithButton("22:31"));

        table.add(createTableCell("20/10/2025"));
        table.add(createTableCellWithButton("9:11"));
        table.add(createTableCellWithButton("22:28"));

        table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));

          table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));

          table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));

          table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));

          table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));

          table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));

          table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));

          table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));

          table.add(createTableCell("21/10/2025"));
        table.add(createTableCellWithButton("9:32"));
        table.add(createTableCellWithButton("22:53"));


        box.add(table);
        return box;
    }

    private Span createTableHeader(String text) {
        Span header = new Span(text);
        header.getStyle()
            .set("font-weight", "600")
            .set("color", "#334155");
        return header;
    }

    private Span createTableCell(String text) {
        Span cell = new Span(text);
        cell.getStyle()
            .set("color", "#475569");
        return cell;
    }

    private HorizontalLayout createTableCellWithButton(String time) {
        HorizontalLayout cell = new HorizontalLayout();
        cell.setSpacing(true);
        cell.getStyle()
            .set("align-items", "center")
            .set("gap", "8px");

        Span timeText = new Span(time);
        timeText.getStyle().set("color", "#475569");

        Button viewButton = new Button("Se svar");
        viewButton.getStyle()
            .set("background", "#f1f5f9")
            .set("color", "#475569")
            .set("border", "1px solid #e2e8f0")
            .set("border-radius", "4px")
            .set("padding", "2px 8px")
            .set("font-size", "12px")
            .set("cursor", "pointer");

        viewButton.addClickListener(e -> {
            // Handle view button click - show survey details dialog
            showSurveyDetails(time);
        });

        cell.add(timeText, viewButton);
        return cell;
    }

    private void showSurveyDetails(String time) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Søvnundersøgelse Svar - " + time);

        VerticalLayout content = new VerticalLayout();
        content.add(new Span("Survey details for " + time + " will be displayed here."));

        dialog.add(content);

        Button closeButton = new Button("Luk", e -> dialog.close());
        dialog.getFooter().add(closeButton);

        dialog.open();
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
        card.getStyle()
            .set("background", "white")
            .set("border-radius", "12px")
            .set("padding", "16px")
            .set("width", "240px")
            .set("box-shadow", "0 1px 6px rgba(15,23,42,0.04)");

        Span t = new Span(title);
        t.getStyle()
            .set("color", "#64748b")
            .set("font-size", "13px");

        Span v = new Span(value);
        v.getStyle()
            .set("font-size", "26px")
            .set("font-weight", "700");

        card.add(t, v);
        return card;
    }



    public static class SleepEntry {
        private LocalDate date;
        private double duration;

        public SleepEntry() {}

        public SleepEntry(LocalDate date, double duration) {
            this.date = date;
            this.duration = duration;

        }

        public LocalDate getDate() { return date; }
        public double getDuration() { return duration; }

        public void setDate(LocalDate date) { this.date = date; }
        public void setDuration(double duration) { this.duration = duration; }

    }
}

package com.example.application.views;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.example.application.database.ClDiDB.Answers.DurationAnswer;
import com.example.application.database.ClDiDB.Questions.GenericQuestion;
import com.example.application.model.AnswerPayload.DurationPayload;
import com.example.application.model.AnsweredEveningSurvey;
import com.example.application.model.AnsweredMorningSurvey;
import com.example.application.model.AnsweredSurvey;
import com.example.application.model.Citizen;
import com.example.application.model.Model;
import com.example.application.security.SecurityUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
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

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;



@JsModule("./SleepStats.js")
@JavaScript("https://cdn.jsdelivr.net/npm/chart.js")
@Route(value = "sleep-stats/:citizenId", layout = MainLayout.class)
@PageTitle("Søvnstatistik")
@RolesAllowed({"ADVISOR", "ADMIN"})
@PermitAll
public class SleepStats extends VerticalLayout implements BeforeEnterObserver{
    private UUID citizenId;
    private Citizen currentCitizen;

    private Model model;

    private final Grid<SleepEntry> grid = new Grid<>(SleepEntry.class, false);
    private final List<SleepEntry> entries = new ArrayList<>();
    // Mock stats for initial display
    private SleepStatsData stats = new SleepStatsData(
        Duration.ofMinutes(555),
        Duration.ofMinutes(481),
        0.90,
        Duration.ofMinutes(15),
        Duration.ofMinutes(40),
        4.0
    );
    private Div createSurveyAnswersBox() {
        return createSurveyAnswersBox(UUID.randomUUID()); // calls main method with dummy UUID
    }

    public SleepStats(Model model) {
        this.model = model;
        model.initAsAdvisor(SecurityUtils.getUsername());
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

        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH.mm");
        AnsweredSurvey[] surveys = { new AnsweredMorningSurvey(UUID.randomUUID(), new GenericQuestion[0], ZonedDateTime.now()) };
        final String timeInBed;
        Optional<Integer> maybeMinutesInBedAccumulator = Optional.empty();

        for (AnsweredSurvey survey : surveys) {
            GenericQuestion<?>[] answers = survey.getAnswers();

            for (GenericQuestion<?> answer : answers) {
                if (answer.getMainQuestionTitle() == "Efter jeg slukkede lyset, sov jeg ca. efter:"
                   && answer.getAnswer().getPayloadClass() == DurationPayload.class
                ) {
                    final Integer temp;
                    if (maybeMinutesInBedAccumulator.isEmpty()) {
                        final Integer i = maybeMinutesInBedAccumulator.orElseThrow();
                        temp = i + ((DurationPayload)(answer.getAnswer().toPayload())).minutes();
                    } else {
                        temp = 0;
                    }
                    maybeMinutesInBedAccumulator = Optional.of(temp);
                }
            }
        }
        if (maybeMinutesInBedAccumulator.isEmpty()) {
            timeInBed = "N/A";
        } else {
            Integer m = maybeMinutesInBedAccumulator.orElseThrow();
            timeInBed = String.format("%02dt %02dm", Math.floor(m/60), Math.floor(m));
        }

//        String eveningTime = survey.getWhenAnswered().format(format);

        // Stats cards
        HorizontalLayout statsRow = new HorizontalLayout();
        statsRow.setWidthFull();
        statsRow.setSpacing(true);

        statsRow.add(
            createStatCard("TIB - Tid i seng", formatDuration(stats.getTib())),
            createStatCard("TST - Total Søvntid", formatDuration(stats.getTst())),
            createStatCard("Søvneffektivitet", formatPercentage(stats.getSleepEfficiency())),
            createStatCard("SOL - Indsovningstid", formatDuration(stats.getSol())),
            createStatCard("WASO - Opvågninger", formatDuration(stats.getWaso())),
            createStatCard("Morgenfølelse", formatRating(stats.getMorningFeeling()))
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
        Div surveyBox = createSurveyAnswersBox();
        // Sleep Survey Answers Box
        surveyWrapper.add(surveyBox);
        add(surveyWrapper);

        refreshGrid();
    }

    public class SleepStatsData {
        private Duration tib;   // Time in Bed
        private Duration tst;   // Total Sleep Time
        private double sleepEfficiency;
        private Duration sol;   // Sleep Onset Latency
        private Duration waso;  // Wake After Sleep Onset
        private double morningFeeling;

        // Constructor
        public SleepStatsData(Duration tib, Duration tst, double sleepEfficiency,
                            Duration sol, Duration waso, double morningFeeling) {
            this.tib = tib;
            this.tst = tst;
            this.sleepEfficiency = sleepEfficiency;
            this.sol = sol;
            this.waso = waso;
            this.morningFeeling = morningFeeling;
        }

        // Getters
        public Duration getTib() { return tib; }
        public Duration getTst() { return tst; }
        public double getSleepEfficiency() { return sleepEfficiency; }
        public Duration getSol() { return sol; }
        public Duration getWaso() { return waso; }
        public double getMorningFeeling() { return morningFeeling; }
    }




    private String formatDuration(Duration d) {
    long hours = d.toHours();
    long minutes = d.minusHours(hours).toMinutes();
    return hours > 0
            ? hours + "t " + minutes + "m"
            : minutes + "m";
    }

    private String formatPercentage(double value) {
        return Math.round(value * 100) + "%";
    }

    private String formatRating(double rating) {
        return String.format("%.1f/5", rating);
    }



    public final class Formatters {

        private Formatters() {}

        public static final DateTimeFormatter DATE_DK =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        public static final DateTimeFormatter TIME =
                DateTimeFormatter.ofPattern("HH:mm");

        public static final DateTimeFormatter DATE_TIME =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    public class SleepSurveyAnswer {
        private LocalDate date;
        private LocalTime morningTime;
        private LocalTime eveningTime;

        public SleepSurveyAnswer(LocalDate date, LocalTime morningTime, LocalTime eveningTime) {
        this.date = date;
        this.morningTime = morningTime;
        this.eveningTime = eveningTime;
    }

        public LocalDate getDate() { return date; }
        public LocalTime getMorningTime() { return morningTime; }
        public LocalTime getEveningTime() { return eveningTime; }
    }

    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().get("citizenId")
            .ifPresent(idParam -> {
                try {

                    // get UUID, may error
                    citizenId = UUID.fromString(idParam);

                    // Get citizen, may error
                    this.currentCitizen = model.getCitizenWithID(citizenId).orElseThrow();

                    // Update UI for this citizen
                    loadCitizenData();

                } catch (IllegalArgumentException e) {
                    // TODO Handle invalid ID format
                } catch (NoSuchElementException e) {
                    // TODO Handle ID not found
                }
            }
        );
    }

    private void loadCitizenData() {

        // Load sleep entries for grid
        this.entries.clear();


        List<SleepEntry> entries = new ArrayList<>();
        for (AnsweredSurvey s : currentCitizen.getSurveys()) {
            switch (s) {

                case AnsweredMorningSurvey m ->
                    entries.add(new SleepEntry(
                        m.getWhenAnswered().toLocalDate(),
                        ((DurationAnswer)(m.getAnswers()[4].getAnswer())).getAnswerInHours()
                    ));

                case AnsweredEveningSurvey e ->
                    entries.add(new SleepEntry(
                        e.getWhenAnswered().toLocalDate(),
                        0.0 // or another relevant field if available
                    ));
            }
        }

        entries.sort((a, b) -> a.getDate().compareTo(b.getDate()));

        this.entries.addAll(entries);
        refreshGrid();

        // Load dynamic stats from DB
        //SleepStatsData stats = db.getSleepStatsForCitizen(citizen.getId());

        // Update the stats row
        HorizontalLayout statsRow = new HorizontalLayout();
        statsRow.setWidthFull();
        statsRow.setSpacing(true);

        statsRow.add(
            createStatCard("TIB - Tid i seng", formatDuration(stats.getTib())),
            createStatCard("TST - Total Søvntid", formatDuration(stats.getTst())),
            createStatCard("Søvneffektivitet", formatPercentage(stats.getSleepEfficiency())),
            createStatCard("SOL - Indsovningstid", formatDuration(stats.getSol())),
            createStatCard("WASO - Opvågninger", formatDuration(stats.getWaso())),
            createStatCard("Morgenfølelse", formatRating(stats.getMorningFeeling()))
        );

        addComponentAtIndex(1, statsRow); // Insert below breadcrumbs

        // Load survey answers dynamically
        Div surveyBox = createSurveyAnswersBox(currentCitizen.getID());

        addComponentAtIndex(3, surveyBox); // Adjust index based on your layout
    }

    private Div createSurveyAnswersBox(UUID citizenId) {
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

        // --- MOCK DATA ---
        List<SleepSurveyAnswer> answers = List.of(
            new SleepSurveyAnswer(LocalDate.of(2025, 10, 19), LocalTime.of(9, 54), LocalTime.of(22, 31)),
            new SleepSurveyAnswer(LocalDate.of(2025, 10, 20), LocalTime.of(9, 50), LocalTime.of(22, 25)),
            new SleepSurveyAnswer(LocalDate.of(2025, 10, 21), LocalTime.of(9, 45), LocalTime.of(22, 20)),
            new SleepSurveyAnswer(LocalDate.of(2025, 10, 22), LocalTime.of(9, 55), LocalTime.of(22, 30)),
            new SleepSurveyAnswer(LocalDate.of(2025, 10, 23), LocalTime.of(9, 50), LocalTime.of(22, 15))
        );
        // --- END MOCK DATA ---

        for (SleepSurveyAnswer answer : answers) {
            table.add(createTableCell(answer.getDate()));
            table.add(createTableCellWithButton(answer.getMorningTime()));
            table.add(createTableCellWithButton(answer.getEveningTime()));
        }

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

    private Span createTableCell(LocalDate date) {
        return createTableCell(date.format(Formatters.DATE_DK));
    }

    private HorizontalLayout createTableCellWithButton(LocalTime time) {
        HorizontalLayout cell = new HorizontalLayout();
        cell.setSpacing(true);
        cell.getStyle()
            .set("align-items", "center")
            .set("gap", "8px");

        Span timeText = new Span(time.format(Formatters.TIME));
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

        viewButton.addClickListener(e -> showSurveyDetails(time));

        cell.add(timeText, viewButton);
        return cell;
    }

    private void showSurveyDetails(LocalTime time) {
        Dialog dialog = new Dialog();

        String formattedTime = time.format(Formatters.TIME);
        dialog.setHeaderTitle("Søvnundersøgelse Svar - " + formattedTime);

        VerticalLayout content = new VerticalLayout();
        content.add(new Span(
            "Survey details for " + formattedTime + " will be displayed here."
        ));

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

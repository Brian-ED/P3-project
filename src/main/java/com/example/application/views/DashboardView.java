package com.example.application.views;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.application.database.ClDiDB.Answers.Answer;
import com.example.application.database.ClDiDB.Answers.ComboBoxAnswer;
import com.example.application.database.ClDiDB.Answers.DurationAnswer;
import com.example.application.database.ClDiDB.Answers.RollAnswer;
import com.example.application.database.ClDiDB.Answers.TextFieldAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateComboboxAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateComboboxRollAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollComboboxAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollRollAnswer;
import com.example.application.model.AnswerPayload;
import com.example.application.model.AnswerPayload.ComboBoxPayload;
import com.example.application.model.AnswerPayload.DurationPayload;
import com.example.application.model.AnswerPayload.RollPayload;
import com.example.application.model.AnswerPayload.TextFieldPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxRollPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollComboboxPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollRollPayload;
import com.example.application.model.AnswerPayload.YesOrNoPayload;
import com.example.application.model.AnsweredSurvey;
import com.example.application.model.Citizen;
import com.example.application.model.DynamicSurvey;
import com.example.application.model.Model;
import com.example.application.model.SleepAdvisor;
import com.example.application.model.SurveyListener;
import com.example.application.model.SurveyType;
import com.example.application.security.SecurityUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("dashboard")
@PageTitle("Søvnrådgiver Dashboard")
@RolesAllowed({ "ADVISOR", "ADMIN" })

public class DashboardView extends VerticalLayout {
    private Span totalCitizensValue;
    private Span myCitizensValue;
    private Span activeTodayValue;
    private Model model;
    private SleepAdvisor meAdvisor;
    private List<com.example.application.model.Citizen> citizens = new ArrayList<>();
    private final static String noName = "";

    private VerticalLayout listContainer;

    public DashboardView(Model model) {
        this.meAdvisor = model.initAsAdvisor(SecurityUtils.getUsername());
        this.model = model;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        // Background of the entire dashboard

        getElement().getStyle().set("background-color", "var(--lumo-base-color)");
        getElement().getStyle().set("font-family", "Inter, Roboto, Arial, sans-serif");
        getElement().getStyle().set("color", "var(--lumo-primary-color)");

        // top margin
        HorizontalLayout topMargin = createTopMargin();
        topMargin.getStyle().set("padding", "24px 40px");
        topMargin.getStyle().set("align-items", "center");
        add(topMargin);

        // Where everthing is going to be (everthing under logout button)
        VerticalLayout content = new VerticalLayout();
        content.setWidthFull();
        content.setPadding(false);
        content.setSpacing(false);
        content.getStyle().set("padding", "0 40px 40px 40px");
        content.getElement().getStyle().set("background-color", "var(--lumo-base-color)");

        // Stats cards row
        HorizontalLayout statsRow = new HorizontalLayout();
        statsRow.setWidthFull();
        statsRow.setSpacing(true);
        statsRow.setAlignItems(Alignment.START);
        statsRow.setJustifyContentMode(JustifyContentMode.CENTER);

        totalCitizensValue = new Span("0");
        totalCitizensValue.getStyle()
            .set("font-size", "28px")
            .set("font-weight", "700")
            .set("color","var(--lumo-body-text-color)");

        myCitizensValue = new Span("0");
        myCitizensValue.getStyle()
            .set("font-size", "28px")
            .set("font-weight", "700")
            .set("color","var(--lumo-body-text-color)");

        activeTodayValue = new Span("0");
        activeTodayValue.getStyle()
            .set("font-size", "28px")
            .set("font-weight", "700")
            .set("color","var(--lumo-body-text-color)");

        Component card1 = createStatCard("Totale borgere", totalCitizensValue, "Registrerede brugere");
        Component card2 = createStatCard("Mine borgere", myCitizensValue, "Tildelte borgere");
        Component card3 = createStatCard("Aktive i dag", activeTodayValue, "Nye indtastninger");

        statsRow.add(card1, card2, card3);

        statsRow.setFlexGrow(1, card1);
        statsRow.setFlexGrow(1, card2);
        statsRow.setFlexGrow(1, card3);

        Div mainCard = new Div();
        mainCard.getStyle().set("border-radius", "12px");
        mainCard.getStyle().set("padding", "20px");
        mainCard.getStyle().set("box-shadow", "0 2px 12px rgba(15,23,42,0.06)");
        mainCard.getStyle().set("margin-top", "18px");
        mainCard.getStyle().set("width", "100%");
        mainCard.getStyle().set("background", "var(--lumo-base2-color)");

        // Title row with icon + search and toggle
        HorizontalLayout mainHeaderRow = new HorizontalLayout();
        mainHeaderRow.setWidthFull();
        mainHeaderRow.setAlignItems(Alignment.CENTER);
        mainHeaderRow.getStyle().set("margin-bottom", "12px");

        // Left title block
        VerticalLayout titleBlock = new VerticalLayout();
        titleBlock.setPadding(false);
        titleBlock.setSpacing(false);

        Span sectionTitle = new Span("Borgersoversigt");
        sectionTitle.getStyle().set("font-weight", "600");
        sectionTitle.getStyle().set("font-size", "15px");
        sectionTitle.getStyle().set("margin", "0");

        Span sectionSub = new Span("Administrer og overvåg borgernes søvndata");
        sectionSub.getStyle().set("color", "var(--lumo-secondary-text-color)");
        sectionSub.getStyle().set("font-size", "13px");

        titleBlock.add(sectionTitle, sectionSub);

        // Search field and toggle on the right
        HorizontalLayout searchAndToggle = new HorizontalLayout();
        searchAndToggle.setSpacing(true);
        searchAndToggle.setAlignItems(Alignment.CENTER);

        TextField search = new TextField();
        search.setPlaceholder("Søg efter borgernavn...");
        search.setWidth("340px");
        search.getStyle().set("min-height", "38px");
        search.getElement().getStyle().set("padding", "6px 10px");
        // simple rounded look
        search.getElement().getStyle().set("border-radius", "8px");
        search.getElement().getStyle().set("border", "1px solid #e6eef8");
        search.getElement().getStyle().set("background", "var(--lumo-base2-color)");

        Checkbox toggleSwitch = new Checkbox("Vis kun mine borgere");

        // Make it look like a slider
        toggleSwitch.getElement().getThemeList().add("toggle");

        // Optional: handle value change
        toggleSwitch.addValueChangeListener(event -> {
            if (event.getValue()) {
                // Toggle ON: show only citizens assigned to me
                refreshList(filterMyCitizens());
            } else {
                // Toggle OFF: show all citizens
                refreshList(citizens);
            }
            updateStats();
        });
        toggleSwitch.addValueChangeListener(event -> {
            if (event.getValue()) {
                // Toggle ON: show only citizens assigned to me
                refreshList(filterMyCitizens());
            } else {
                // Toggle OFF: show all citizens
                refreshList(citizens);
            }
            updateStats();
        });

        ComboBox<String> sortBox = new ComboBox<>();
        sortBox.setPlaceholder("Sorter efter");
        sortBox.setItems("Navn", "Sidste indtastning", "Tilstand (Moderat/Ukendt)");
        sortBox.setWidth("180px");

        searchAndToggle.add(search, sortBox, toggleSwitch);
        search.addValueChangeListener(event -> {
            String filter = event.getValue().trim().toLowerCase();

            // If empty, show all citizens
            if (filter.isEmpty()) {
                refreshList(citizens);
                return;
            }

            // Filter citizens by name
            List<Citizen> filtered = citizens.stream()
                    .filter(c -> c.getFullName().toLowerCase().contains(filter))
                    .toList();

            refreshList(filtered);
        });
        sortBox.addValueChangeListener(
                (com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent<ComboBox<String>, String> e) -> {
                    String selected = e.getValue();
                    if (selected == null)
                        return;

                    switch (selected) {

                        case "Navn":
                            citizens.sort(Comparator.comparing(Citizen::getFullName));
                            break;

                        case "Sidste indtastning":
                            citizens.sort(Comparator.comparing((Citizen c) -> {
                                long runningMax = 0;
                                for (var i : c.getSurveys()) {
                                    runningMax = Math.max(runningMax, i.getWhenAnswered().toEpochSecond());
                                }
                                return runningMax;
                            }).reversed());
                            break;

                        case "Tilstand (Moderat/Ukendt)":
                            citizens.sort(Comparator.comparing(
                                (Citizen c) -> 0)); // TODO  Old code instead of 0: "Moderat".equalsIgnoreCase(getSeverity(c)) ? 0 : 1
                            break;
                    }

                    refreshList();
                });

        mainHeaderRow.add(titleBlock);
        mainHeaderRow.expand(titleBlock);
        mainHeaderRow.add(searchAndToggle);

        // Citizen list container
        listContainer = new VerticalLayout();
        listContainer.setPadding(false);
        listContainer.setSpacing(false);
        listContainer.setWidthFull();

        SleepAdvisor advisor1 = new SleepAdvisor(UUID.randomUUID(), "Søvnrådgiver Anna");
        SleepAdvisor advisor2 = new SleepAdvisor(UUID.randomUUID(), "Søvnrådgiver Peter");

        citizens.add(mockCitizen("Emma Jensen",
                                advisor1));
        citizens.add(mockCitizen("Lars Hansen",
                                advisor2));
        citizens.add(mockCitizen("Maja Sørensen",
                                advisor1));

        // Refresh UI

        refreshList();
        updateStats();

        mainCard.add(mainHeaderRow, listContainer);

        content.add(statsRow, mainCard);
        add(content);

    }

    String getSeverity(Citizen citizen) {
        return "Moderate";
    }

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    String getLastEntryTimeFormatted(Citizen citizen) {
        return getLastEntry(citizen).map(x->x.getWhenAnswered()
                                    .format(dateFormatter))
                                    .orElse("Ingen indtastninger");
    }

    Optional<AnsweredSurvey> getLastEntry(Citizen citizen) {

        if (citizen.getSurveys().size() <= 0) {
            return Optional.empty();
        }

        long runningMax = 0;
        AnsweredSurvey newestAnsweredSurvey = citizen.getSurveys().get(0);

        for (var i : citizen.getSurveys()) {
            long time = i.getWhenAnswered().toEpochSecond();
            if (runningMax < time) {
                newestAnsweredSurvey = i;
            };
            runningMax = Math.max(runningMax, time);
        }
        return Optional.of(newestAnsweredSurvey);
    }

    private class CheckForEnd implements SurveyListener {
        private Boolean hasChangedBool = true; // Changed is true by default since it changes from non-existence to pointing at first question.

        public Boolean hasChanged() {
            Boolean temp = hasChangedBool;
            this.hasChangedBool = false;
            return temp;
        }

        @Override
        public void currentQuestionChanged(int newIndex) {
            hasChangedBool = true;
        }

        @Override
        public void questionAnswered(int index, AnswerPayload payload) {}
    }

    private Citizen mockCitizen(
            String name,
            SleepAdvisor advisor) {
        Citizen citizen = model.createCitizen(name);

        DynamicSurvey survey = model.initDynamicSurvey(SurveyType.morning, citizen);
        CheckForEnd endCheckerListener = new CheckForEnd();
        survey.addListener(endCheckerListener);
        while (endCheckerListener.hasChanged()) {
            System.out.println(survey.currentQuestion());
            Answer<?> a = survey.currentQuestion().getAnswer();
            ZonedDateTime t = ZonedDateTime.now();
            switch (a) {
                case YesOrNoElaborateRollRollAnswer     y  -> {y .answer(new YesOrNoElaborateRollRollPayload    (false, t, t));}
                case YesOrNoElaborateRollAnswer         y2 -> {y2.answer(new YesOrNoElaborateRollPayload        (false, t));}
                case YesOrNoElaborateComboboxRollAnswer y3 -> {y3.answer(new YesOrNoElaborateComboboxRollPayload(false, (short)0, t));}
                case YesOrNoElaborateRollComboboxAnswer y4 -> {y4.answer(new YesOrNoElaborateRollComboboxPayload(false, t, (short)0));}
                case YesOrNoElaborateComboboxAnswer     y5 -> {y5.answer(new YesOrNoElaborateComboboxPayload    (false, (short)0));}
                case ComboBoxAnswer                     c  -> {c .answer(new ComboBoxPayload                    ((short)0));}
                case RollAnswer                         r  -> {r .answer(new RollPayload                        (t));}
                case DurationAnswer                     d  -> {d .answer(new DurationPayload                    (0));}
                case TextFieldAnswer                    tt -> {tt.answer(new TextFieldPayload                   ("I'm doing good, thanks"));}
                case YesOrNoAnswer                      y6 -> {y6.answer(new YesOrNoPayload                     (false));}
            }
            survey.nextQuestion();
        }
        AnsweredSurvey answered = survey.submitAnswers().orElseThrow(() -> new IllegalStateException("Not all questions were answered in mock data"));

        citizen.submitSurvey(answered);
        citizen.setAssignedAdvisor(advisor);

        return citizen;
    }

    private void updateStats() {
        // Total citizens
        totalCitizensValue.setText(String.valueOf(citizens.size()));

        // My citizens
        int myCount = filterMyCitizens().size();
        myCitizensValue.setText(String.valueOf(myCount));

        // Active today
        String today = java.time.LocalDate.now().toString(); // "YYYY-MM-DD"
        long activeToday = citizens.stream()
                .filter(c -> today.equals(getLastEntryTimeFormatted(c)))
                .count();
        activeTodayValue.setText(String.valueOf(activeToday));
    }

    private List<Citizen> filterMyCitizens() {
        return citizens.stream()
                .filter(c -> c.getAssignedAdvisor() != null && c.getAssignedAdvisor().equals(meAdvisor))
                .toList();
    }

    private void refreshList() {
        refreshList(citizens);
    }

    private void refreshList(List<Citizen> list) {
        listContainer.removeAll();
        for (Citizen c : list) {
            listContainer.add(createCitizenRow(c));
        }
    }

    private Div createStatCard(String highlightLabel, Span valueSpan, String label) {
        Div card = new Div();
        card.getStyle().set("background", "var(--lumo-base2-color)");
        card.getStyle().set("border-radius", "12px");
        card.getStyle().set("padding", "18px");
        card.getStyle().set("width", "260px");
        card.getStyle().set("box-shadow", "0 1px 6px rgba(15,23,42,0.04)");
        card.getStyle().set("display", "flex");
        card.getStyle().set("flex-direction", "column");
        card.getStyle().set("gap", "8px");

        Span hl = new Span(highlightLabel);
        hl.getStyle().set("font-size", "16px");
        hl.getStyle().set("font-weight", "600");
        hl.getStyle().set("color", "var(--lumo-body-text-color)");

        Span l = new Span(label);
        l.getStyle().set("color", "#64748b");
        l.getStyle().set("font-size", "13px");

        card.add(hl, valueSpan, l);
        return card;
    }

    private HorizontalLayout createCitizenRow(Citizen c) {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.setPadding(true);
        row.setAlignItems(Alignment.CENTER);
        row.getStyle().set("padding", "12px");
        row.getStyle().set("border-radius", "10px");
        row.getStyle().set("margin-bottom", "8px");
        row.getStyle().set("background",
                "Moderat".equalsIgnoreCase(getSeverity(c)) ? "var(--lumo-moderate-color)" : "transparent"); // light
                                                                                                             // highlight
                                                                                                             // for
                                                                                                             // moderate
        row.getStyle().set("border", "1px solid rgba(15,23,42,0.03)");

        // Avatar circle with initials
        Div avatar = new Div();
        avatar.getStyle().set("width", "40px");
        avatar.getStyle().set("height", "40px");
        avatar.getStyle().set("border-radius", "50%");
        avatar.getStyle().set("display", "flex");
        avatar.getStyle().set("align-items", "center");
        avatar.getStyle().set("justify-content", "center");
        avatar.getStyle().set("background", "var(--lumo-base-color");
        avatar.getStyle().set("color", "#1f2937");
        avatar.getStyle().set("font-weight", "600");
        avatar.getStyle().set("font-size", "14px");
        Span initials = new Span(getInitials(c.getFullName()));
        avatar.add(initials);

        // Info block
        VerticalLayout info = new VerticalLayout();
        info.setPadding(false);
        info.setSpacing(false);

        Span idLabel = new Span("ID: " + c.getID());
        idLabel.getStyle().set("font-size", "12px").set("color", "var(--lumo-secondary-text-color)");

        Span lastSurveyLabel = new Span("Sidste indtastning: " + getLastEntryTimeFormatted(c));
        lastSurveyLabel.getStyle().set("font-size", "13px").set("color", "var(--lumo-secondary-text-color)");

        Span nameLabel = new Span(c.getFullName());
        nameLabel.getStyle().set("font-weight", "600");
        nameLabel.getStyle().set("font-size", "14px");

        Span sub = new Span("Sidste indtastning: " + getLastEntryTimeFormatted(c));
        sub.getStyle().set("color", "var(--lumo-secondary-text-color)");
        sub.getStyle().set("font-size", "13px");

        info.add(nameLabel, idLabel, lastSurveyLabel);
        info.getStyle().set("margin-left", "12px");

        // Right-side actions: severity badge, advisor label (placeholder), sort icon,
        // view data button
        HorizontalLayout rightActions = new HorizontalLayout();
        rightActions.setSpacing(true);
        rightActions.setAlignItems(Alignment.CENTER);

        Span severityBadge = new Span(getSeverity(c));
        severityBadge.getStyle().set("padding", "6px 10px");
        severityBadge.getStyle().set("border-radius", "999px");
        severityBadge.getStyle().set("font-size", "13px");
        severityBadge.getStyle().set("font-weight", "600");
        // color based on severity
        if ("Moderat".equalsIgnoreCase(getSeverity(c))) {
            severityBadge.getStyle().set("background", "var(--lumo-base2-color)");
            severityBadge.getStyle().set("color", "#92400e");
            severityBadge.getStyle().set("border", "1px solid rgba(245,158,11,0.12)");
        } else {
            severityBadge.getStyle().set("background", "var(--lumo-base2-color)");
            severityBadge.getStyle().set("color", "var(--lumo-body-text-color)");
            severityBadge.getStyle().set("border", "1px solid rgba(99,102,241,0.08)");
        }

        ComboBox<String> advisorCombo = new ComboBox<>();
        advisorCombo.setPlaceholder("Vælg søvnrådgiver");

        // Options in the dropdown
        String[] advisorNames = model.getAllAdvisorNames();
        String[] items = new String[advisorNames.length+1];

        items[0] = noName;
        for (int i=0; i<advisorNames.length; i++) {
            items[i+1] = advisorNames[i];
        }
        advisorCombo.setItems(items);

        // Default selection
        c.getAssignedAdvisor().ifPresentOrElse(advisor -> advisorCombo.setValue(advisor.getFullName()),
                                                    () -> advisorCombo.setValue(noName));

        // Styling (similar to your Span)
        advisorCombo.getStyle().set("color", "var(--lumo-secondary-text-color)");
        advisorCombo.getStyle().set("font-size", "13px");
        advisorCombo.getStyle().set("padding", "6px 8px");
        advisorCombo.getStyle().set("border-radius", "8px");
        advisorCombo.getStyle().set("border", "1px solid rgba(15,23,42,0.04)");
        advisorCombo.getStyle().set("background", "transparent");
        advisorCombo.setWidth("200px");

        advisorCombo.addValueChangeListener(event -> {
            String selected = event.getValue();
            if (selected != null) {
                model.setAssignedAdvisorByName(c, selected);
            }
        });
        Button viewData = new Button("Se data", new Icon(VaadinIcon.CHART));
        viewData.getElement().getStyle().set("border-radius", "8px");
        viewData.getElement().getStyle().set("background", "white");
        viewData.getElement().getStyle().set("border", "1px solid rgba(15,23,42,0.06)");
        viewData.getElement().getStyle().set("padding", "6px 10px");
        viewData.getElement().getStyle().set("font-size", "13px");
        viewData.addClickListener(e -> viewData.getUI().ifPresent(ui -> ui.navigate("sleep-stats/" + c.getID())));
        rightActions.add(severityBadge, advisorCombo, viewData);

        // Expand info to use remaining space
        row.add(avatar, info);
        row.expand(info);
        row.add(rightActions);

        return row;
    }

    private HorizontalLayout createTopMargin() {
        HorizontalLayout top = new HorizontalLayout();
        top.setWidthFull();
        top.setAlignItems(Alignment.CENTER);
        // Setup user
        // Left: small app title
        Span appTitle = new Span("Velkommen," + meAdvisor.getFullName());
        appTitle.getStyle().set("font-weight", "700");
        appTitle.getStyle().set("font-size", "26px");
        appTitle.getStyle().set("color", "#072d85ff");

        top.add(appTitle);
        top.expand(appTitle);

        return top;
    }

    private String getInitials(String name) {
        if (name == null || name.isBlank())
            return "";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        } else {
            return ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase();
        }
    }

}

package com.example.application.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.postgresqlSwitcher;
import com.example.application.database.PostgreSQLDatabaseControler;
import com.example.application.database.ClDiDB.AdvisorRow;
import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.model.Citizen;
import com.example.application.model.SleepAdvisor;
import com.example.application.model.AnsweredSurvey;
import com.example.application.model.Model;
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
@RolesAllowed({"ADVISOR", "ADMIN"})


public class DashboardView extends VerticalLayout {
    private Span totalCitizensValue;
    private Span myCitizensValue;
    private Span activeTodayValue;
    private String username;
    private Model model;
    private List<com.example.application.model.Citizen> citizens = new ArrayList<>();

    private VerticalLayout listContainer;
    private final PostgreSQLDatabaseControler db;
    public DashboardView(PostgreSQLDatabaseControler db, Model model) {
        Citizen citizen = model.getThisCitizen(SecurityUtils.getUsername());
        this.username = citizen != null ? citizen.getFullName() : "Bruger";
        this.model = model;
        this.db = db;

        
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        //Background of the entire dashboard

        getElement().getStyle().set("background-color", "var(--lumo-base-color)");
        getElement().getStyle().set("font-family", "Inter, Roboto, Arial, sans-serif");
        getElement().getStyle().set("color", "var(--lumo-primary-color)");

        //top margin
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
        totalCitizensValue.getStyle().set("font-size", "28px").set("font-weight", "700").set("color", "var(--lumo-body-text-color)");

        myCitizensValue = new Span("0");
        myCitizensValue.getStyle().set("font-size", "28px").set("font-weight", "700").set("color", "var(--lumo-body-text-color)");

        activeTodayValue = new Span("0");
        activeTodayValue.getStyle().set("font-size", "28px").set("font-weight", "700").set("color", "var(--lumo-body-text-color)");

        Component card1 = createStatCard("Totale borgere", totalCitizensValue, "Registrerede brugere");
        Component card2 = createStatCard("Mine borgere", myCitizensValue, "Tildelte borgere");
        Component card3 = createStatCard("Aktive i dag", activeTodayValue, "Nye indtastninger");

        statsRow.add(card1,card2,card3);

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
            if (selected == null) return;

            switch (selected) {

                case "Navn":
                    citizens.sort(Comparator.comparing(Citizen::getFullName));
                    break;

                case "Sidste indtastning":
                    citizens.sort(Comparator
                            .comparing((Citizen c) -> {
                                String v = c.getLastEntry();
                                return v.equals("Ingen indtastninger") || v.equals("-") ? "0000-00-00" : v;
                            })
                            .reversed()
                    );
                    break;

                case "Tilstand (Moderat/Ukendt)":
                    citizens.sort(Comparator.comparing(
                            (Citizen c) -> "Moderat".equalsIgnoreCase(c.getSeverity()) ? 0 : 1)
                    );
                    break;
            }

            refreshList();
        }
);

        mainHeaderRow.add(titleBlock);
        mainHeaderRow.expand(titleBlock);
        mainHeaderRow.add(searchAndToggle);

        // Citizen list container
        listContainer = new VerticalLayout();
        listContainer.setPadding(false);
        listContainer.setSpacing(false);
        listContainer.setWidthFull();

       List<com.example.application.model.Citizen> dbCitizens = Arrays.asList(db.searchCitizensByName(""));
        SleepAdvisor advisor1 = new SleepAdvisor(new AdvisorRow());
advisor1.getRow().setFullName("Søvnrådgiver Anna");

SleepAdvisor advisor2 = new SleepAdvisor(new AdvisorRow());
advisor2.getRow().setFullName("Søvnrådgiver Peter");

// Example citizens
Citizen citizen1 = mockCitizen(1, "Emma Jensen", "Moderat", "2025-12-14", advisor1);
Citizen citizen2 = mockCitizen(2, "Lars Hansen", "Ukendt", "2025-12-13", advisor2);
Citizen citizen3 = mockCitizen(3, "Maja Sørensen", "Moderat", "2025-12-12", advisor1);

// Add to your citizens list
citizens.add(citizen1);
citizens.add(citizen2);
citizens.add(citizen3);
// Refresh UI

       citizens.addAll(dbCitizens);

        refreshList();
        updateStats();

        mainCard.add(mainHeaderRow, listContainer);

        content.add(statsRow, mainCard);
        add(content);

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
                               .filter(c -> today.equals(c.getLastEntry()))
                               .count();
    activeTodayValue.setText(String.valueOf(activeToday));
}
    private SleepAdvisor getCurrentAdvisor() {
    List<SleepAdvisor> advisors = db.getAllAdvisors();
    return advisors.stream()
                   .filter(a -> a.getFullName().equals(username))
                   .findFirst()
                   .orElse(null);
}

private List<Citizen> filterMyCitizens() {
    SleepAdvisor currentAdvisor = getCurrentAdvisor();
    if (currentAdvisor == null) return new ArrayList<>();
    return citizens.stream()
                   .filter(c -> c.getAdvisor() != null && c.getAdvisor().equals(currentAdvisor))
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
        row.getStyle().set("background", c.isHighlight() ? "var(--lumo-moderate-color)" : "transparent"); // light highlight for moderate
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

        Span idLabel = new Span("ID: " + c.getId());
        idLabel.getStyle().set("font-size", "12px").set("color", "var(--lumo-secondary-text-color)");

        Span lastSurveyLabel = new Span("Sidste indtastning: " + c.getLastEntry());
        lastSurveyLabel.getStyle().set("font-size", "13px").set("color", "var(--lumo-secondary-text-color)");

        Span nameLabel = new Span(c.getFullName());
        nameLabel.getStyle().set("font-weight", "600");
        nameLabel.getStyle().set("font-size", "14px");

        Span sub = new Span("Sidste indtastning: " + c.getLastEntry());
        sub.getStyle().set("color", "var(--lumo-secondary-text-color)");
        sub.getStyle().set("font-size", "13px");

        info.add(nameLabel, idLabel, lastSurveyLabel);
        info.getStyle().set("margin-left", "12px");

        // Right-side actions: severity badge, advisor label (placeholder), sort icon, view data button
        HorizontalLayout rightActions = new HorizontalLayout();
        rightActions.setSpacing(true);
        rightActions.setAlignItems(Alignment.CENTER);

        Span severityBadge = new Span(c.getSeverity());
        severityBadge.getStyle().set("padding", "6px 10px");
        severityBadge.getStyle().set("border-radius", "999px");
        severityBadge.getStyle().set("font-size", "13px");
        severityBadge.getStyle().set("font-weight", "600");
        // color based on severity
        if ("Moderat".equalsIgnoreCase(c.getSeverity())) {
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
List<SleepAdvisor> advisors = db.getAllAdvisors(); // We’ll add this method
List<String> advisorNames = advisors.stream().map(SleepAdvisor::getFullName).toList();
advisorCombo.setItems(advisorNames);
advisorCombo.setValue(c.getAdvisor());

// Default selection
advisorCombo.setValue(c.getAdvisor());

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
        db.getAdvisorByName(selected).ifPresent(advisor -> {
            c.setAdvisor(advisor);  // sets Optional<SleepAdvisor>
            db.getCitizenByName(c.getFullName()).ifPresent(citizen -> {
                citizen.setAdvisor(advisor);
                db.saveCitizen(citizen);
                });
            });
        }
    });
        Button viewData = new Button("Se data", new Icon(VaadinIcon.CHART));
        viewData.getElement().getStyle().set("border-radius", "8px");
        viewData.getElement().getStyle().set("background", "white");
        viewData.getElement().getStyle().set("border", "1px solid rgba(15,23,42,0.06)");
        viewData.getElement().getStyle().set("padding", "6px 10px");
        viewData.getElement().getStyle().set("font-size", "13px");
        viewData.addClickListener(e -> 
        viewData.getUI().ifPresent(ui -> ui.navigate("sleep-stats/" + c.getId())));
        rightActions.add(severityBadge, advisorCombo, viewData);

        // Expand info to use remaining space
        row.add(avatar, info);
        row.expand(info);
        row.add(rightActions);

        return row;
    }

private Citizen mockCitizen(int id, String name, String severity, String lastEntry, SleepAdvisor advisor) {
    CitizenRow row = new CitizenRow();
    row.setFullName(name); // You can only set full name in CitizenRow
    
    Citizen c = new Citizen(row);
    
    // Set the ID (your Citizen class has a private Long id that you can access via reflection or constructor)
    try {
        java.lang.reflect.Field field = Citizen.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(c, (long) id);
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Set advisor
    c.setAdvisor(advisor);

    // Severity and lastEntry are computed dynamically in your current Citizen class
    // For mocking, you can extend Citizen or override getSeverity/getLastEntry if needed
    return c;
}


    private HorizontalLayout createTopMargin() {
        HorizontalLayout top = new HorizontalLayout();
        top.setWidthFull();
        top.setAlignItems(Alignment.CENTER);
        // Setup user
        // Left: small app title
        Span appTitle = new Span("Velkommen,"+ username);
        appTitle.getStyle().set("font-weight", "700");
        appTitle.getStyle().set("font-size", "26px");
        appTitle.getStyle().set("color", "#072d85ff");

            top.add(appTitle);
            top.expand(appTitle);

            return top;
    }

    private String getInitials(String name) {
        if (name == null || name.isBlank()) return "";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        } else {
            return ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase();
        }
    }
    
}

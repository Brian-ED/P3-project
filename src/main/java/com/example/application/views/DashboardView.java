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

    private List<com.example.application.model.Citizen> citizens = new ArrayList<>();

    private VerticalLayout listContainer;
    private final PostgreSQLDatabaseControler db;
    public DashboardView(PostgreSQLDatabaseControler db) {
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

        Component card1 = createStatCard("Totale borgere" ,"6", "Registrerede brugere");
        Component card2 = createStatCard("Mine borgere" ,"0", "Tildelte borgere");
        Component card3 = createStatCard("Aktive i dag" ,"0", "Nye indtastninger");

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
                System.out.println("Toggle ON");
            } else {
                System.out.println("Toggle OFF");
            }
        });

        ComboBox<String> sortBox = new ComboBox<>();
        sortBox.setPlaceholder("Sorter efter");
        sortBox.setItems("Navn", "Sidste indtastning", "Tilstand (Moderat/Ukendt)");
        sortBox.setWidth("180px");

        searchAndToggle.add(search, sortBox, toggleSwitch);

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
Citizen citizen1 = new Citizen(new CitizenRow());
citizen1.getRow().setFullName("Emma Jensen");
citizen1.setAdvisor(advisor1);

Citizen citizen2 = new Citizen(new CitizenRow());
citizen2.getRow().setFullName("Lars Hansen");
citizen2.setAdvisor(advisor2);

Citizen citizen3 = new Citizen(new CitizenRow());
citizen3.getRow().setFullName("Maja Sørensen");
citizen3.setAdvisor(advisor1);

// Add to your citizens list
citizens.add(citizen1);
citizens.add(citizen2);
citizens.add(citizen3);

// Refresh UI
refreshList();

       citizens.addAll(dbCitizens);

        refreshList();


        mainCard.add(mainHeaderRow, listContainer);

        content.add(statsRow, mainCard);
        add(content);

    }


    private void refreshList() {
         listContainer.removeAll();
    for (Citizen c : citizens) {
        listContainer.add(createCitizenRow(c)); // pass the full object
    }
}
    private Div createStatCard(String highlightlabel, String value, String label) {
        Div card = new Div();
        card.getStyle().set("background", "var(--lumo-base2-color)");
        card.getStyle().set("border-radius", "12px");
        card.getStyle().set("padding", "18px");
        card.getStyle().set("width", "260px");
        card.getStyle().set("box-shadow", "0 1px 6px rgba(15,23,42,0.04)");
        card.getStyle().set("display", "flex");
        card.getStyle().set("flex-direction", "column");
        card.getStyle().set("gap", "8px");

        Span hl = new Span(highlightlabel);
        hl.getStyle().set("font-size", "16px");
        hl.getStyle().set("font-weight", "600");
        hl.getStyle().set("color", "var(--lumo-body-text-color)");

        Span v = new Span(value);
        v.getStyle().set("font-size", "28px");
        v.getStyle().set("font-weight", "700");
        v.getStyle().set("color", "var(--lumo-body-text-color)");

        Span l = new Span(label);
        l.getStyle().set("color", "#64748b");
        l.getStyle().set("font-size", "13px");

        card.add(hl, v, l);
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

        info.add(nameLabel, idLabel, lastSurveyLabel, sub);
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

        // Left: small app title
        Span appTitle = new Span("Velkommen, John Doe");
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

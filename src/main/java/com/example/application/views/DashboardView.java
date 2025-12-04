package com.example.application.views;

import java.util.*;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

import com.example.application.model.Citizen;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
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

    private List<Citizen> citizens = new ArrayList<>();
    private VerticalLayout listContainer;

    public DashboardView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        //Background of the entire dashboard
        getElement().getStyle().set("background-color", "#f7f7f7ff");
        getElement().getStyle().set("font-family", "Inter, Roboto, Arial, sans-serif");
        getElement().getStyle().set("color", "#0467e7ff");

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
        content.getElement().getStyle().set("background-color", "#f7f7f7ff");

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
        mainCard.getStyle().set("background", "white");
        mainCard.getStyle().set("border-radius", "12px");
        mainCard.getStyle().set("padding", "20px");
        mainCard.getStyle().set("box-shadow", "0 2px 12px rgba(15,23,42,0.06)");
        mainCard.getStyle().set("margin-top", "18px");
        mainCard.getStyle().set("width", "100%");

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
        sectionSub.getStyle().set("color", "#64748b");
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
        search.getElement().getStyle().set("background", "#fff");

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
                    citizens.sort(Comparator.comparing(Citizen::getName));
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

        // Add citizens (sample rows) — replicate the visual style from screenshot
        citizens.add(new Citizen("Parsa Gholami", "Ingen indtastninger", "Ukendt", false));
        citizens.add(new Citizen("Alexander Ølholm", "2025-10-19", "Moderat", true));
        citizens.add(new Citizen("Patrick Kure", "2025-10-20", "Ukendt", false));
        citizens.add(new Citizen("Brian Ellingsgaard", "2025-10-19", "Ukendt", false));
        citizens.add(new Citizen("Jonas", "2025-10-18", "Ukendt", false));
        citizens.add(new Citizen("John Doe", "-", "Ukendt", false));

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
        card.getStyle().set("background", "white");
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
        hl.getStyle().set("color", "#0f172a");

        Span v = new Span(value);
        v.getStyle().set("font-size", "28px");
        v.getStyle().set("font-weight", "700");
        v.getStyle().set("color", "#0f172a");

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
        row.getStyle().set("background", c.isHighlight() ? "#fffbeb" : "transparent"); // light highlight for moderate
        row.getStyle().set("border", "1px solid rgba(15,23,42,0.03)");

        // Avatar circle with initials
        Div avatar = new Div();
        avatar.getStyle().set("width", "40px");
        avatar.getStyle().set("height", "40px");
        avatar.getStyle().set("border-radius", "50%");
        avatar.getStyle().set("display", "flex");
        avatar.getStyle().set("align-items", "center");
        avatar.getStyle().set("justify-content", "center");
        avatar.getStyle().set("background", "#eef2ff");
        avatar.getStyle().set("color", "#1f2937");
        avatar.getStyle().set("font-weight", "600");
        avatar.getStyle().set("font-size", "14px");
        avatar.add(new Span(getInitials(c.getName())));

        // Info block
        VerticalLayout info = new VerticalLayout();
        info.setPadding(false);
        info.setSpacing(false);

        Span nameLabel = new Span(c.getName());
        nameLabel.getStyle().set("font-weight", "600");
        nameLabel.getStyle().set("font-size", "14px");

        Span sub = new Span("Sidste indtastning: " + c.getLastEntry());
        sub.getStyle().set("color", "#64748b");
        sub.getStyle().set("font-size", "13px");

        info.add(nameLabel, sub);
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
            severityBadge.getStyle().set("background", "#fff7ed");
            severityBadge.getStyle().set("color", "#92400e");
            severityBadge.getStyle().set("border", "1px solid rgba(245,158,11,0.12)");
        } else {
            severityBadge.getStyle().set("background", "#eef2ff");
            severityBadge.getStyle().set("color", "#0f172a");
            severityBadge.getStyle().set("border", "1px solid rgba(99,102,241,0.08)");
        }

 ComboBox<String> advisorCombo = new ComboBox<>();
advisorCombo.setPlaceholder("Vælg søvnrådgiver");

// Options in the dropdown
advisorCombo.setItems(
        "Ingen søvnrådgiver",
        "Anna Hansen",
        "Peter Nielsen",
        "Maria Jensen",
        "John Doe"
);

// Default selection
advisorCombo.setValue(c.getAdvisor());

// Styling (similar to your Span)
advisorCombo.getStyle().set("color", "#64748b");
advisorCombo.getStyle().set("font-size", "13px");
advisorCombo.getStyle().set("padding", "6px 8px");
advisorCombo.getStyle().set("border-radius", "8px");
advisorCombo.getStyle().set("border", "1px solid rgba(15,23,42,0.04)");
advisorCombo.getStyle().set("background", "transparent");
advisorCombo.setWidth("200px");

    advisorCombo.addValueChangeListener(event -> {
        String selected = event.getValue();
        if (selected != null) {
            c.setAdvisor(selected); 
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

    static class Citizen {
        private final String name;
        private final String lastEntry;
        private final String severity;
        private final boolean highlight;

        private String advisor;

        Citizen(String name, String lastEntry, String severity, boolean highlight) {
            this.name = name;
            this.lastEntry = lastEntry;
            this.severity = severity;
            this.highlight = highlight;
            this.advisor = "Ingen søvnrådgiver"; 
        }
        
        public String getName() { return name; }
        public String getLastEntry() { return lastEntry; }
        public String getSeverity() { return severity; }
        public boolean isHighlight() { return highlight; }

        public String getAdvisor() { return advisor; }

        public void setAdvisor(String advisor) { this.advisor = advisor; }
    }

}

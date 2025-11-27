package com.example.application.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Citizen")
@RolesAllowed({"CITIZEN", "ADMIN"})
@PageTitle("Klient Dashboard")
public class Citizen extends VerticalLayout {
    public Citizen() {
        // Creates variables that contains the current time so it can be used dynamically
        LocalDateTime now = LocalDateTime.now();
        String currentDate = now.format(DateTimeFormatter.ofPattern("EEEE 'den' d. MMMM yyyy")); // getAnsweredDate()
        String currentTime = now.format(DateTimeFormatter.ofPattern("HH.mm")); // getAnsweredTime()

        // Gets the name of the user so that it can be used to write "velkommen, Username"
        String Username = "Navn"; // getUserName()

        // Creates the header beam
        HorizontalLayout header = new HorizontalLayout();
        header.setPadding(true);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        VerticalLayout leftSide = new VerticalLayout();
        H2 h2 = new H2("Velkommen, " + Username);
        h2.getStyle().set("color", "darkblue");
        leftSide.setSpacing(false);
        leftSide.setPadding(false);
        leftSide.add(
            h2
        );

        Button logoutButton = new Button("Log ud");
        logoutButton.getStyle()
        .set("background-color","darkblue")
        .set("color","white")
        .set("cursor", "pointer");

        header.add(leftSide, logoutButton);
        add(header);
        Hr hr = new Hr();
        hr.getStyle()
        .set("margin-top", "-20px")
        .set("margin-bottom", "40px");
        add(hr);

        // Creates the "morninganswer box"
        Card morningCard = new Card();
        morningCard.getStyle().set("padding", "20px");
        H2 morningH2 = new H2("Morgensvar");
        morningH2.getElement().setProperty("innerHTML", 
    "<span style='color: orange;'>✹</span> Morgensvar");
        morningH2.getStyle().set("padding", "20px");
        Span morningSpan = new Span("Udfyld dit morgenskema om nattens søvn");
        morningSpan.getStyle().set("padding", "20px");
        Button morningButton = new Button("Udfyld aftensvar");
        morningButton.setWidthFull();
        morningButton.getStyle()
        .set("background-color", "darkblue")
        .set("color", "white")
        .set("padding", "20px")
        .set("margin-top", "20px")
        .set("cursor", "pointer");
        morningCard.setWidth("100%");
        morningCard.add(
            morningH2, 
            morningSpan, 
            morningButton
        );

        // Creates the "eveninganswer box"
        Card eveningCard = new Card();
        eveningCard.getStyle().set("padding", "20px");
        H2 eveningH2 = new H2("Aftensvar");
        eveningH2.getElement().setProperty("innerHTML", 
    "<span style='color: purple;'>☾</span> Aftensvar");
        eveningH2.getStyle().set("padding", "20px");
        Span eveningSpan = new Span("Udfyld dit aftensskema om dagens aktiviteter");
        eveningSpan.getStyle().set("padding", "20px");
        Button eveningButton = new Button("Udfyld aftensvar");
        eveningButton.setWidthFull();
        eveningButton.getStyle()
        .set("background-color", "darkblue")
        .set("color", "white")
        .set("padding", "20px")
        .set("margin-top", "20px")
        .set("cursor", "pointer");
        eveningCard.setWidth("100%");
        eveningCard.add(
            eveningH2, 
            eveningSpan, 
            eveningButton
        );

        // Place morning and evening answer side by side
        HorizontalLayout sideBySideCards1 = new HorizontalLayout(morningCard, eveningCard);
        sideBySideCards1.setWidthFull();
        sideBySideCards1.setSpacing(true);
        add(sideBySideCards1);

        // Create the Div to see the latest morning aswers
        Div latestMorningAnswersDiv = new Div();
        latestMorningAnswersDiv.getStyle()
        .set("padding", "20px")
        .set("cursor", "pointer")
        .set("background-color", "lightgray")
        .set("margin-top", "1px")
        .set("border-radius", "8px");
        latestMorningAnswersDiv.setWidth("100%");
        H3 latestMorningH3 = new H3();
        latestMorningH3.getElement().setProperty("innerHTML", 
    "<span style='color: orange;'>✹</span> Morgensvar");
        Span registeredMorning = new Span("Registreret");
        registeredMorning.getStyle()
        .set("padding", "25px");
        latestMorningAnswersDiv.add(
            latestMorningH3,
            registeredMorning
        );

        latestMorningAnswersDiv.addClickListener(e -> {
            Dialog dialog = new Dialog();
            Button morningDialogButton = new Button("Luk", click -> dialog.close());
            HorizontalLayout morningHorizontal = new HorizontalLayout();
            VerticalLayout leftSideMorning = new VerticalLayout();
            H3 h3Morning = new H3("Morgensvarshistorik:");
            h3Morning.getStyle().set("color", "darkblue");
            leftSideMorning.setSpacing(false);
            leftSideMorning.setPadding(false);
            leftSideMorning.add(
            h3Morning
        );
            morningHorizontal.setPadding(true);
            morningHorizontal.setWidthFull();
            morningHorizontal.setJustifyContentMode(JustifyContentMode.BETWEEN);
            morningHorizontal.setAlignItems(Alignment.CENTER);
            morningHorizontal.add(leftSideMorning, morningDialogButton);
            morningHorizontal.getStyle().set("margin-top", "-30px");
            morningDialogButton.getStyle()
            .set("background-color","darkblue")
            .set("color","white")
            .set("cursor", "pointer");
            dialog.setWidth("60%");
            VerticalLayout listLayout = new VerticalLayout();
            listLayout.setSpacing(true);
            listLayout.setPadding(true);

            Button viewDataButton1 = new Button("Se data", new Icon(VaadinIcon.CHART));
            viewDataButton1.getElement().getStyle()
            .set("border-radius", "8px")
            .set("background", "white")
            .set("border", "1px solid rgba(15,23,42,0.06)")
            .set("padding", "6px 10px")
            .set("font-size", "13px")
            .set("margin-left", "100px");
            Hr hr1 = new Hr();
            Button viewDataButton2 = new Button("Se data", new Icon(VaadinIcon.CHART));
            viewDataButton2.getElement().getStyle()
            .set("border-radius", "8px")
            .set("background", "white")
            .set("border", "1px solid rgba(15,23,42,0.06)")
            .set("padding", "6px 10px")
            .set("font-size", "13px")
            .set("margin-left", "100px");
            Hr hr2 = new Hr();
            Button viewDataButton3 = new Button("Se data", new Icon(VaadinIcon.CHART));
            viewDataButton3.getElement().getStyle()
            .set("border-radius", "8px")
            .set("background", "white")
            .set("border", "1px solid rgba(15,23,42,0.06)")
            .set("padding", "6px 10px")
            .set("font-size", "13px")
            .set("margin-left", "100px");
            Hr hr3 = new Hr();

            Div entry1 = new Div();
            entry1.add(new Span("Morgensvar - Registreret 10.40"), viewDataButton1, hr1);

            Div entry2 = new Div();
            entry2.add(new Span("Morgensvar - Registreret 09.15"), viewDataButton2, hr2);

            Div entry3 = new Div();
            entry3.add(new Span("Morgensvar - Registreret 08.30"), viewDataButton3, hr3);

            listLayout.add(entry1, entry2, entry3);
            dialog.add(morningHorizontal, listLayout);
            
            dialog.open();
        });

        // Create the Div to see the latest evening aswers
        Div latestEveningAnswersDiv = new Div();
        latestEveningAnswersDiv.getStyle()
        .set("padding", "20px")
        .set("cursor", "pointer")
        .set("background-color", "lightgray")
        .set("margin-top", "1px")
        .set("border-radius", "8px");
        latestEveningAnswersDiv.setWidth("100%");
        H3 latestEveningH3 = new H3();
        latestEveningH3.getElement().setProperty("innerHTML", 
    "<span style='color: purple;'>☾</span> Aftensvar");
        Span registeredEvening = new Span("Registreret");
        registeredEvening.getStyle()
        .set("padding", "18px");
        latestEveningAnswersDiv.add(
            latestEveningH3,
            registeredEvening
        );

        latestEveningAnswersDiv.addClickListener(e -> {
            Dialog dialog = new Dialog();
            Button eveningDialogButton = new Button("Luk", click -> dialog.close());
            HorizontalLayout eveningHorizontal = new HorizontalLayout();
            VerticalLayout leftSideEvening = new VerticalLayout();
            H3 h3Morning = new H3("Aftensvarshistorik:");
            h3Morning.getStyle().set("color", "darkblue");
            leftSideEvening.setSpacing(false);
            leftSideEvening.setPadding(false);
            leftSideEvening.add(
            h3Morning
        );
            eveningHorizontal.setPadding(true);
            eveningHorizontal.setWidthFull();
            eveningHorizontal.setJustifyContentMode(JustifyContentMode.BETWEEN);
            eveningHorizontal.setAlignItems(Alignment.CENTER);
            eveningHorizontal.add(leftSideEvening, eveningDialogButton);
            eveningHorizontal.getStyle().set("margin-top", "-30px");
            eveningDialogButton.getStyle()
            .set("background-color","darkblue")
            .set("color","white")
            .set("cursor", "pointer");
            dialog.setWidth("60%");
            VerticalLayout listLayout = new VerticalLayout();
            listLayout.setSpacing(true);
            listLayout.setPadding(true);

            Button viewDataButton1 = new Button("Se data", new Icon(VaadinIcon.CHART));
            viewDataButton1.getElement().getStyle()
            .set("border-radius", "8px")
            .set("background", "white")
            .set("border", "1px solid rgba(15,23,42,0.06)")
            .set("padding", "6px 10px")
            .set("font-size", "13px")
            .set("margin-left", "100px");
            Hr hr1 = new Hr();
            Button viewDataButton2 = new Button("Se data", new Icon(VaadinIcon.CHART));
            viewDataButton2.getElement().getStyle()
            .set("border-radius", "8px")
            .set("background", "white")
            .set("border", "1px solid rgba(15,23,42,0.06)")
            .set("padding", "6px 10px")
            .set("font-size", "13px")
            .set("margin-left", "100px");
            Hr hr2 = new Hr();
            Button viewDataButton3 = new Button("Se data", new Icon(VaadinIcon.CHART));
            viewDataButton3.getElement().getStyle()
            .set("border-radius", "8px")
            .set("background", "white")
            .set("border", "1px solid rgba(15,23,42,0.06)")
            .set("padding", "6px 10px")
            .set("font-size", "13px")
            .set("margin-left", "100px");
            Hr hr3 = new Hr();

            Div entry1 = new Div();
            entry1.add(new Span("Aftensvar - Registreret 20.40"), viewDataButton1, hr1);

            Div entry2 = new Div();
            entry2.add(new Span("Aftensvar - Registreret 21.15"), viewDataButton2, hr2);

            Div entry3 = new Div();
            entry3.add(new Span("Aftensvar - Registreret 22.30"), viewDataButton3, hr3);

            listLayout.add(entry1, entry2, entry3);
            dialog.add(eveningHorizontal, listLayout);
            
            dialog.open();
        });

        // Place latest morning and latest evening answer side by side
        HorizontalLayout sideBySideCards2 = new HorizontalLayout(latestMorningAnswersDiv, latestEveningAnswersDiv);
        sideBySideCards2.setWidthFull();
        sideBySideCards2.setSpacing(true);

        // Creates the "Seneste indtastninger box"
        Card latestAnswersCard = new Card();
        latestAnswersCard.getStyle()
        .set("margin-top", "20px")
        .set("display", "block")
        .set("padding", "16px");
        H3 lastestAnswerH3 = new H3("📆︎ Seneste indtastninger");
        lastestAnswerH3.getStyle()
        .set("padding", "20px")
        .set("margin-bottom", "-35px");
        Span lastestAnswerSpan1 = new Span("Oversigt over dine seneste søvnregistreringer:");
        lastestAnswerSpan1.getStyle().set("padding", "20px").set("display", "block");
        Span lastestAnswerSpan2 = new Span(currentDate);
        lastestAnswerSpan2.getStyle()
        .set("padding", "20px")
        .set("margin-top", "0px")
        .set("margin-bottom", "-10px")
        .set("font-weight", "bold")
        .set("display", "block");
        latestAnswersCard.setWidthFull();
        latestAnswersCard.add(
            lastestAnswerH3, 
            lastestAnswerSpan1,
            lastestAnswerSpan2
        );
        // Adds the "latestMorningAnswersCard" and the "latestEveningAnswersCard" into the "latestAnswersCard"
        latestAnswersCard.add(sideBySideCards2);
        add(latestAnswersCard);
    }
}

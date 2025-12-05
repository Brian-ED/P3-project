package com.example.application.views;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.vaadin.flow.component.accordion.Accordion;
import com.example.application.model.Answer;
import com.example.application.model.AnsweredSurvey;
import com.example.application.model.Citizen;
import com.example.application.model.Model;
import com.example.application.model.SurveyType;
import com.example.application.security.SecurityUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("citizen")
@RolesAllowed({"CITIZEN", "ADMIN"})
@PageTitle("Klient Dashboard")
public class CitizenView extends VerticalLayout {

    private final Model model;

    public CitizenView(Model model) {
        this.model = model;
        // Setup user
        Citizen citizen = model.getCitizen().orElse(model.initCitizen(SecurityUtils.getUsername())); // TODO handle this error case better. There should be an error screen since this shouldn't happen. It would happen if a advisor came into this site and somehow got access.
        String Username = citizen.getFullName();
        AnsweredSurvey[] pastSurveys = citizen.getSurveys(); // TODO @Jonas should figure out how to integrate this into this site and display all surveys, not just one.
        AnsweredSurvey temporaryMorningSurvey = new AnsweredSurvey(new Answer<?>[0], SurveyType.morning, ZonedDateTime.now());
        AnsweredSurvey temporaryEveningSurvey = new AnsweredSurvey(new Answer<?>[0], SurveyType.evening, ZonedDateTime.now());

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
        .set("border-radius", "8px")
        .set("text-align", "center");
        latestMorningAnswersDiv.setWidth("100%");
        H3 latestMorningH3 = new H3();
        Span tekstHistorik = new Span("Historik");
        latestMorningH3.add(tekstHistorik);
        latestMorningAnswersDiv.add(latestMorningH3);

        latestMorningAnswersDiv.addClickListener(e -> {
            Dialog dialog = new Dialog();
            Button morningDialogButton = new Button("Luk", click -> dialog.close());
            HorizontalLayout morningHorizontal = new HorizontalLayout();
            VerticalLayout leftSideMorning = new VerticalLayout();
            H3 h3Morning = new H3("Historik:");
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
            .set("margin-left", "10px");
            Hr hr1 = new Hr();

            viewDataButton1.addClickListener(event -> {
                Dialog dataDialog = new Dialog();
                dataDialog.setHeight("90%");
                Button morningDialogButton1 = new Button("Luk", click -> dataDialog.close());
                HorizontalLayout morningHorizontal1 = new HorizontalLayout();
                VerticalLayout leftSideMorning1 = new VerticalLayout();
                H3 h3Morning1 = new H3("Morningsurvey");
                H3 h3Evening1 = new H3("Eveningsurvey");
                leftSideMorning1.setSpacing(false);
                leftSideMorning1.setPadding(false);
                leftSideMorning1.add(
                h3Morning1
                );
                
                morningHorizontal1.setPadding(true);
                morningHorizontal1.setWidthFull();
                morningHorizontal1.setJustifyContentMode(JustifyContentMode.BETWEEN);
                morningHorizontal1.setAlignItems(Alignment.CENTER);
                morningHorizontal1.add(leftSideMorning1, morningDialogButton1);
                morningHorizontal1.getStyle().set("margin-top", "-30px");
                morningDialogButton1.getStyle()
                .set("background-color","darkblue")
                .set("color","white")
                .set("cursor", "pointer");
                dialog.setWidth("60%");
                VerticalLayout listLayout1 = new VerticalLayout();
                listLayout1.setSpacing(true);
                listLayout1.setPadding(true);
                Span span1 = new Span("Skal voksne sove 7-9 timer pr. nat? - Svar: Ja");
                Span span2 = new Span("Er REM-søvn den fase hvor vi drømmer mest? - Svar: Ja");
                Span span3 = new Span("Kan mangel på søvn påvirke koncentrationen? - Svar: Ja");
                Span span4 = new Span("Er kaffe godt at drikke lige før sengetid? - Svar: Nej");
                Span span5 = new Span("Kaldes søvnløshed for insomnia? - Svar: Ja");
                Span span6 = new Span("Påvirker blåt lys fra skærme søvnen negativt? - Svar: Ja");
                Span span7 = new Span("Er det sundt at sove med lys tændt? - Svar: Nej");
                Span span8 = new Span("Kan regelmæssig motion forbedre søvnkvaliteten? - Svar: Ja");
                Span span9 = new Span("Er søvnapnø en ufarlig tilstand? - Svar: Nej");
                Span span10 = new Span("Er døgnrytme det samme som cirkadisk rytme? - Svar: Ja");
                listLayout1.add(span1, span2, span3, span4, span5, span6, span7, span8, span9, span10, h3Evening1, span1, span2, span3, span4);
                dataDialog.add(morningHorizontal1, listLayout1);
                dataDialog.open();
            });

            Button viewDataButton2 = new Button("Se data", new Icon(VaadinIcon.CHART));
            viewDataButton2.getElement().getStyle()
            .set("border-radius", "8px")
            .set("background", "white")
            .set("border", "1px solid rgba(15,23,42,0.06)")
            .set("padding", "6px 10px")
            .set("font-size", "13px")
            .set("margin-left", "10px");
            Hr hr2 = new Hr();
            viewDataButton2.addClickListener(event -> {
                Dialog dataDialog = new Dialog();
            });
            Button viewDataButton3 = new Button("Se data", new Icon(VaadinIcon.CHART));
            viewDataButton3.getElement().getStyle()
            .set("border-radius", "8px")
            .set("background", "white")
            .set("border", "1px solid rgba(15,23,42,0.06)")
            .set("padding", "6px 10px")
            .set("font-size", "13px")
            .set("margin-left", "10px");
            Hr hr3 = new Hr();
            viewDataButton3.addClickListener(event -> {
                Dialog dataDialog = new Dialog();
            });
            Span s1 = new Span("(Morgensvar: 10.40) - (Aftensvar: 20.35)"); 
            s1.getElement().getStyle()
            .set("margin-left", "200px");
            Span s2 = new Span("(Morgensvar: 09.30) - (Aftensvar: 21.15)");
            s2.getElement().getStyle()
            .set("margin-left", "200px");
            Span s3 = new Span("(Morgensvar: 08.05) - (Aftensvar: 22.50)");
            s3.getElement().getStyle()
            .set("margin-left", "200px");

            Div entry1 = new Div();
            entry1.getStyle().set("white-space", "pre");
            entry1.add(new Span("Svar - Registreret: 04/12/2025"), s1, viewDataButton1, hr1);

            Div entry2 = new Div();
            entry2.getStyle().set("white-space", "pre");
            entry2.add(new Span("Svar - Registreret: 03/12/2025"), s2, viewDataButton2, hr2);

            Div entry3 = new Div();
            entry3.getStyle().set("white-space", "pre");
            entry3.add(new Span("Svar - Registreret: 02/12/2025"), s3, viewDataButton3, hr3);

            listLayout.add(entry1, entry2, entry3);
            dialog.add(morningHorizontal, listLayout);

            dialog.open();
        });

        // Place latest morning and latest evening answer side by side
        HorizontalLayout sideBySideCards2 = new HorizontalLayout(latestMorningAnswersDiv);
        sideBySideCards2.setWidthFull();
        sideBySideCards2.setSpacing(true);

        // Creates the "Seneste indtastninger box"
        Card latestAnswersCard = new Card();
        latestAnswersCard.getStyle()
        .set("margin-top", "20px")
        .set("display", "block")
        .set("margin", "0 auto")
        .set("padding", "16px");
        H3 lastestAnswerH3 = new H3("📆︎ Seneste indtastninger");
        lastestAnswerH3.getStyle()
        .set("padding", "20px")
        .set("margin-bottom", "-35px");
        Span lastestAnswerSpan1 = new Span("Oversigt over dine seneste søvnregistreringer:");
        lastestAnswerSpan1.getStyle().set("padding", "20px").set("display", "block");
        Span lastestAnswerSpan2 = new Span(temporaryMorningSurvey.whenAnswered.format(DateTimeFormatter.ofPattern("EEEE 'den' d. MMMM yyyy")));
        lastestAnswerSpan2.getStyle()
        .set("padding", "20px")
        .set("margin-top", "0px")
        .set("margin-bottom", "-10px")
        .set("font-weight", "bold")
        .set("display", "block");
        latestAnswersCard.setWidth("50%");
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

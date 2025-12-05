package com.example.application.views;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload;
import com.example.application.model.AnswerPayload.ComboBoxPayload;
import com.example.application.model.AnswerPayload.RollPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxPayload;
import com.example.application.model.AnswerPayload.YesOrNoPayload;
import com.example.application.model.AnsweredSurvey;
import com.example.application.model.Citizen;
import com.example.application.model.Model;
import com.example.application.model.Question;
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
        getElement().getStyle().set("background-color", "#f7f7f7ff");
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

        header.add(leftSide);
        add(header);
        Hr hr = new Hr();
        hr.getStyle()
        .set("margin-top", "-20px")
        .set("margin-bottom", "40px");
        add(hr);

        // Creates the "morninganswer box"
        Card morningCard = new Card();
        morningCard.getStyle()
        .set("background-color", "white")
        .set("box-shadow", "0 2px 12px rgba(15,23,42,0.06)")
        .set("padding", "20px");
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
        eveningCard.getStyle()
        .set("background-color", "white")
        .set("box-shadow", "0 2px 12px rgba(15,23,42,0.06)")
        .set("padding", "20px");
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

        //Listens if the "History button" is clicked
        latestMorningAnswersDiv.addClickListener(e -> {
            Dialog dialog = new Dialog();
            Button morningDialogButton = new Button("Luk", click -> dialog.close());
            HorizontalLayout morningHorizontal = new HorizontalLayout();
            VerticalLayout leftSideMorning = new VerticalLayout();
            H3 h3Morning = new H3("Historik:");
            h3Morning.getStyle().set("color", "darkblue");
            leftSideMorning.setSpacing(false);
            leftSideMorning.setPadding(false);
            leftSideMorning.add(h3Morning);

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

            // HENT ALLE BESVAREDE SKEMAER FRA CITIZEN
            AnsweredSurvey[] allSurveys = citizen.getSurveys();

            // Gruppér skemaer efter dato
            Map<LocalDate, List<AnsweredSurvey>> surveysByDate = new TreeMap<>(Collections.reverseOrder());
    
            for (AnsweredSurvey survey : allSurveys) {
                LocalDate date = survey.getWhenAnswered().toLocalDate();
                surveysByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(survey);
            }
    
            // Generér en entry for hver dato (nyeste først)
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH.mm");

            for (Map.Entry<LocalDate, List<AnsweredSurvey>> entry : surveysByDate.entrySet()) {
                LocalDate date = entry.getKey();
                List<AnsweredSurvey> surveysOnDate = entry.getValue();
        
                // Find morgen- og aftenskemaer for denne dato
                String morningTime = null;
                String eveningTime = null;
                AnsweredSurvey morningSurvey = null;
                AnsweredSurvey eveningSurvey = null;

                for (AnsweredSurvey survey : surveysOnDate) {
                    if (survey.getType() == SurveyType.morning) {
                        morningTime = survey.getWhenAnswered().format(timeFormatter);
                        morningSurvey = survey;
                    } else if (survey.getType() == SurveyType.evening) {
                        eveningTime = survey.getWhenAnswered().format(timeFormatter);
                        eveningSurvey = survey;
                    }
                }
        
                // Opret kun entry hvis mindst et skema er udfyldt
                if (morningTime != null || eveningTime != null) {
                    // Byg teksten for tidspunkter præcis som på billedet
                    StringBuilder timeText = new StringBuilder();
                    if (morningTime != null) {
                        timeText.append("(Morgensvar: ").append(morningTime).append(")");
                    }
                    if (eveningTime != null) {
                        if (morningTime != null) {
                            timeText.append(" - ");
                        }
                        timeText.append("(Aftensvar: ").append(eveningTime).append(")");
                    }
            
                // Opret "Se data" knap med ikon
                Button viewDataButton = new Button("Se data", new Icon(VaadinIcon.CHART));
                viewDataButton.getElement().getStyle()
                    .set("border-radius", "8px")
                    .set("background", "white")
                    .set("border", "1px solid rgba(15,23,42,0.06)")
                    .set("padding", "6px 10px")
                    .set("font-size", "13px")
                    .set("margin-left", "10px");
                    
                // Tilføj click listener til knappen
                LocalDate currentDate = date;
                AnsweredSurvey finalMorningSurvey = morningSurvey;
                AnsweredSurvey finalEveningSurvey = eveningSurvey;
                String finalMorningTime = morningTime;
                String finalEveningTime = eveningTime;
                    
                viewDataButton.addClickListener(event -> {
                    Dialog dataDialog = new Dialog();
                    dataDialog.setHeight("90%");
                    dataDialog.setWidth("80%");

                    Button closeButton = new Button("Luk", click -> dataDialog.close());
                    HorizontalLayout headerLayout = new HorizontalLayout();
                    VerticalLayout leftSideHeader = new VerticalLayout();

                    H3 dialogTitle = new H3("Skemadata for " + date.format(dateFormatter));
                    leftSideHeader.setSpacing(false);
                    leftSideHeader.setPadding(false);
                    leftSideHeader.add(dialogTitle);

                    headerLayout.setPadding(true);
                    headerLayout.setWidthFull();
                    headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
                    headerLayout.setAlignItems(Alignment.CENTER);
                    headerLayout.add(leftSideHeader, closeButton);
                    headerLayout.getStyle().set("margin-top", "-30px");

                    closeButton.getStyle()
                        .set("background-color", "darkblue")
                        .set("color", "white")
                        .set("cursor", "pointer")
                        .set("padding", "10px 20px");

                    VerticalLayout contentLayout = new VerticalLayout();
                    contentLayout.setSpacing(true);
                    contentLayout.setPadding(true);
                    contentLayout.setWidthFull();

                    // Tilføj tidspunkter øverst i dialogen
                    Div timeInfoDiv = new Div();
                    if (finalMorningTime != null && finalEveningTime != null) {
                        timeInfoDiv.add(new Span("Morgensvar kl. " + finalMorningTime + " | Aftensvar kl. " + finalEveningTime));
                    } else if (finalMorningTime != null) {
                        timeInfoDiv.add(new Span("Morgensvar kl. " + finalMorningTime));
                    } else if (finalEveningTime != null) {
                        timeInfoDiv.add(new Span("Aftensvar kl. " + finalEveningTime));
                    }
                    timeInfoDiv.getStyle()
                        .set("margin-bottom", "20px")
                        .set("font-weight", "bold")
                        .set("color", "#666");
                    contentLayout.add(timeInfoDiv);

                    // Vis morgenundersøgelse hvis den findes
                    if (finalMorningSurvey != null) {
                        H3 morningTitle = new H3("Morgenskema");
                        morningTitle.getStyle()
                            .set("color", "orange")
                            .set("margin-bottom", "15px")
                            .set("margin-top", "0");
                        contentLayout.add(morningTitle);

                        // Vis alle svar fra morgenundersøgelsen
                        Answer<?>[] morningAnswers = finalMorningSurvey.getAnswers();
                        if (morningAnswers != null && morningAnswers.length > 0) {
                            for (Answer<?> answer : morningAnswers) {
                                Div answerDiv = new Div();
                                Span questionSpan = new Span(getQuestionText(answer) + ": ");
                                questionSpan.getStyle().set("font-weight", "bold");

                                Span answerValueSpan = new Span(getAnswerValue(answer));
                                answerValueSpan.getStyle().set("color", "#0066cc");

                                answerDiv.add(questionSpan, answerValueSpan);
                                answerDiv.getStyle()
                                    .set("margin-left", "15px")
                                    .set("margin-bottom", "8px")
                                    .set("padding", "5px 10px")
                                    .set("background-color", "#f5f5f5")
                                    .set("border-radius", "4px");
                                contentLayout.add(answerDiv);
                            }
                        } else {
                            Span noAnswers = new Span("Ingen svar registreret for morgenskemaet");
                            noAnswers.getStyle()
                                .set("color", "gray")
                                .set("font-style", "italic")
                                .set("margin-left", "15px");
                            contentLayout.add(noAnswers);
                        }

                        // Tilføj separator kun hvis der også er aftenundersøgelse
                        if (finalEveningSurvey != null) {
                            Hr separator = new Hr();
                            separator.getStyle()
                                .set("margin-top", "20px")
                                .set("margin-bottom", "20px");
                            contentLayout.add(separator);
                        }
                    }

                    // Vis aftenundersøgelse hvis den findes
                    if (finalEveningSurvey != null) {
                        H3 eveningTitle = new H3("Aftenskema");
                        eveningTitle.getStyle()
                            .set("color", "purple")
                            .set("margin-bottom", "15px");

                        // Hvis der ikke var morgenundersøgelse, fjern top margin
                        if (finalMorningSurvey == null) {
                            eveningTitle.getStyle().set("margin-top", "0");
                        }

                        contentLayout.add(eveningTitle);

                        // Vis alle svar fra aftenundersøgelse
                        Answer<?>[] eveningAnswers = finalEveningSurvey.getAnswers();
                        if (eveningAnswers != null && eveningAnswers.length > 0) {
                            for (Answer<?> answer : eveningAnswers) {
                                Div answerDiv = new Div();
                                Span questionSpan = new Span(getQuestionText(answer) + ": ");
                                questionSpan.getStyle().set("font-weight", "bold");

                                Span answerValueSpan = new Span(getAnswerValue(answer));
                                answerValueSpan.getStyle().set("color", "#0066cc");

                                answerDiv.add(questionSpan, answerValueSpan);
                                answerDiv.getStyle()
                                    .set("margin-left", "15px")
                                    .set("margin-bottom", "8px")
                                    .set("padding", "5px 10px")
                                    .set("background-color", "#f5f5f5")
                                    .set("border-radius", "4px");
                                contentLayout.add(answerDiv);
                            }
                        } else {
                            Span noAnswers = new Span("Ingen svar registreret for aftenskemaet");
                            noAnswers.getStyle()
                                .set("color", "gray")
                                .set("font-style", "italic")
                                .set("margin-left", "15px");
                            contentLayout.add(noAnswers);
                        }
                    }

                    // Hvis ingen skemaer har svar (dette burde ikke ske da vi kun opretter knap hvis mindst et er udfyldt)
                    if (finalMorningSurvey == null && finalEveningSurvey == null) {
                        Span noData = new Span("Ingen skemadata tilgængelig for denne dato");
                        noData.getStyle()
                            .set("color", "gray")
                            .set("font-style", "italic")
                            .set("text-align", "center")
                            .set("padding", "40px");
                        contentLayout.add(noData);
                    }
                
                    dataDialog.add(headerLayout, contentLayout);
                    dataDialog.open();
                });
            
                // Opret spans for data entry - PRÆCIS SOM PÅ BILLEDET
                Span dateSpan = new Span("Svar - Registreret: " + date.format(dateFormatter));
                dateSpan.getStyle()
                    .set("display", "inline-block")
                    .set("font-weight", "500");
            
                Span timeSpan = new Span(timeText.toString());
                timeSpan.getElement().getStyle()
                    .set("margin-left", "200px")
                    .set("display", "inline-block")
                    .set("color", "#666");
            
                Hr HR = new Hr();
                hr.getStyle()
                    .set("margin-top", "10px")
                    .set("margin-bottom", "10px");
            
                // Opret entry container - struktur præcis som på billedet
                Div entryDiv = new Div();
                entryDiv.getStyle()
                    .set("white-space", "nowrap")
                    .set("padding", "10px 0");
            
                // Tilføj elementer i den rigtige rækkefølge: dato → tider → knap
                entryDiv.add(dateSpan);
                entryDiv.add(timeSpan);
                entryDiv.add(viewDataButton);
                entryDiv.add(hr);
            
                listLayout.add(entryDiv);
            }
            // Hvis både morningTime og eveningTime er null, oprettes INGEN linje
        }
    
            // Hvis der ikke er nogen data
            if (listLayout.getComponentCount() == 0) {
                Span noDataSpan = new Span("Ingen skemadata fundet");
                noDataSpan.getStyle()
                    .set("color", "gray")
                    .set("font-style", "italic")
                    .set("padding", "20px");
                listLayout.add(noDataSpan);
            }
    
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
        .set("box-shadow", "0 2px 12px rgba(15,23,42,0.06)")
        .set("background-color", "white")
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
    /**
     * Henter spørgsmålsteksten fra et Answer objekt
     * NOTE: Vi skal gætte at Answer har en reference til Question
     * Hvis ikke, skal vi overveje en anden struktur
     */
    private String getQuestionText(Answer<?> answer) {
        // Dette afhænger af hvordan Answer klasserne er implementeret
        // Hvis Answer har et question felt:
        try {
            java.lang.reflect.Field questionField = answer.getClass().getDeclaredField("question");
            questionField.setAccessible(true);
            Question question = (Question) questionField.get(answer);
            return question.questionTitle;
        } catch (Exception e) {
            // Reflection fejlede - Answer har måske ikke et question felt
        }
        
        // Alternativt - hvis Answer gemmer spørgsmåls-ID eller noget andet
        return "Spørgsmål"; // Placeholder indtil vi ser Answer-implementationerne
    }
    
    
    //Henter og formaterer svaret fra et Answer objekt
    private String getAnswerValue(Answer<?> answer) {
        try {
            // Brug reflection for at få payload fra Answer
            // Dette afhænger af hvordan Answer klasserne gemmer data
            java.lang.reflect.Method getPayloadMethod = answer.getClass().getMethod("getPayload");
            AnswerPayload payload = (AnswerPayload) getPayloadMethod.invoke(answer);
            
            if (payload != null) {
                return formatAnswerPayload(payload);
            }
        } catch (Exception e) {
            // Prøv en anden tilgang
        }
        
        return "Ikke besvaret";
    }
    
    
    //Formaterer AnswerPayload til en læsbar tekst
    private String formatAnswerPayload(AnswerPayload payload) {
        if (payload == null) {
            return "Ikke besvaret";
        }
        
        // Behandl de forskellige typer payload
        if (payload instanceof YesOrNoPayload yesNoPayload) {
            return yesNoPayload.yesNo() ? "Ja" : "Nej";
        }
        
        if (payload instanceof YesOrNoElaborateComboboxPayload elaboratePayload) {
            String base = elaboratePayload.yesNo() ? "Ja" : "Nej";
            if (elaboratePayload.whichIsSelected() > 0) {
                return base + " (" + elaboratePayload.whichIsSelected() + ")";
            }
            return base;
        }
        
        if (payload instanceof RollPayload rollPayload) {
            // Konverter Instant til læselig tid
            java.time.format.DateTimeFormatter formatter = 
                java.time.format.DateTimeFormatter.ofPattern("HH:mm")
                    .withZone(java.time.ZoneId.systemDefault());
            return formatter.format(rollPayload.timestamp());
        }
        
        if (payload instanceof ComboBoxPayload comboBoxPayload) {
            // Her skal vi vide hvilke valgmuligheder der er
            // Returner index eller værdi
            return "Valg " + comboBoxPayload.whichIsSelected();
        }
        
        return payload.toString();
    }

}

package com.example.application.views;

import com.example.application.UI;
import com.example.application.model.AnswerPayload;
import com.example.application.model.Citizen;
import com.example.application.model.DynamicSurvey;
import com.example.application.model.Model;
import com.example.application.model.SurveyListener;
import com.example.application.model.SurveyType;
import com.example.application.security.SecurityUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("survey/:type")
@RolesAllowed({"ADMIN", "CITIZEN"})
public class Survey extends VerticalLayout implements BeforeEnterObserver {

    DynamicSurvey survey;

    class ThisListener implements SurveyListener {
        @Override
        public void currentQuestionChanged(int newIndex) {
            showQuestion(UI.drawUI(survey.currentQuestion()));
        }

        @Override
        public void questionAnswered(int index, AnswerPayload payload) {}
    }

    // Layout that will contain the current question UI
    public VerticalLayout content;

    private Model model;
    private Citizen thisCitizen;

    public Survey(Model model) {
        this.model = model;
        this.thisCitizen = model.initAsCitizen(SecurityUtils.getUsername());
        Button next = new Button("Næste >");
        Button prev = new Button("< Tilbage");
        H3 h3 = new H3("Spørgeskema");

        // Layout setup
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.START);
        setJustifyContentMode(JustifyContentMode.BETWEEN);

        this.content = new VerticalLayout();
        content.setAlignItems(FlexComponent.Alignment.END);
        content.setWidth("60%");
        content.setPadding(true);
        content.getStyle().set("overflow", "visible");

        HorizontalLayout buttons = new HorizontalLayout(prev, next);
        buttons.setWidthFull();
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Style buttons
        next.setWidth("220px");
        next.setHeight("110px");
        prev.setWidth("220px");
        prev.setHeight("110px");
        prev.getStyle().set("background-color", "darkblue").set("color", "white");
        next.getStyle().set("background-color", "darkblue").set("color", "white");
        prev.getStyle().set("font-size", "30px");
        next.getStyle().set("font-size", "30px");

        add(h3, content, buttons);

        // Next/Prev logic (works once survey is initialized in beforeEnter)
        next.addClickListener(e -> { if (survey != null) survey.nextQuestion(); });
        prev.addClickListener(e -> { if (survey != null) survey.previousQuestion(); });
    }

    private void showQuestion(Component qComponent) {
        content.removeAll();                        // remove old question UI
        content.add(qComponent);                    // show it
        content.setAlignSelf(FlexComponent.Alignment.START, qComponent);
        content.getStyle().set("overflow", "visible");
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String type = event.getRouteParameters().get("type").orElse("morning");

        SurveyType surveyType;
        if (type.equalsIgnoreCase("morning")) {
            surveyType = SurveyType.morning;
        } else if (type.equalsIgnoreCase("evening")) {
            surveyType = SurveyType.evening;
        } else {
            event.rerouteTo("citizen");
            return;
        }

        this.survey = model.initDynamicSurvey(surveyType, thisCitizen);

        survey.addListener(new ThisListener());

        showQuestion(UI.drawUI(survey.currentQuestion()));
    }
}

package com.example.application.views;

import com.example.application.QuestionUI;
import com.example.model.AnswerPayload;
import com.example.model.DynamicSurvey;
import com.example.model.Question;
import com.example.model.SurveyListener;
import com.example.model.SurveyType;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("survey")
@RolesAllowed({"ADMIN", "CITIZEN"})
public class Survey extends VerticalLayout {

    DynamicSurvey survey;

    class ThisListener implements SurveyListener {
        @Override
        public void currentQuestionChanged(int newIndex) {
            showQuestion(QuestionUI.drawUI(survey.currentQuestion()));
        }

        @Override
        public void questionAnswered(int index, AnswerPayload payload) {
            // TODO
        }
    }

    // Layout that will contain the current question UI
    public VerticalLayout content;

    public Survey() {

        // 1) Choose which survey to show
        this.survey = new DynamicSurvey(SurveyType.morning);
        SurveyListener listener = new ThisListener();
        survey.addListener(listener);

        Button next = new Button("Næste >");
        Button prev = new Button("< Tilbage");
        H3 h3 = new H3("Spørgeskema");

        // 2) Overall layout setup for the page
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.START);
        setJustifyContentMode(JustifyContentMode.BETWEEN);

        // 3) Layout that holds the *current* question
        this.content = new VerticalLayout();
        content.setAlignItems(FlexComponent.Alignment.END);
        content.setWidth("60%");
        content.setPadding(true);

        // 4) Button bar
        HorizontalLayout buttons = new HorizontalLayout(prev, next);
        buttons.setWidthFull();
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // 5) Style buttons
        next.setWidth("220px");
        next.setHeight("110px");
        prev.setWidth("220px");
        prev.setHeight("110px");
        prev.getStyle().set("background-color", "darkblue").set("color", "white");
        next.getStyle().set("background-color", "darkblue").set("color", "white");
        prev.getStyle().set("font-size", "30px");
        next.getStyle().set("font-size", "30px");

        // 6) Add heading + content + buttons to the page
        add(h3, content, buttons);

        // 7) Show the first question
		showQuestion(QuestionUI.drawUI(survey.currentQuestion()));

        // 8) Next / Previous button logic
        next.addClickListener(e -> survey.nextQuestion());
        prev.addClickListener(e -> survey.previousQuestion());
    }

    private void showQuestion(Component qComponent) {
        content.removeAll();                        // remove old question UI
        content.add(qComponent);                    // show it
        content.setAlignSelf(FlexComponent.Alignment.START, qComponent);
    }
}

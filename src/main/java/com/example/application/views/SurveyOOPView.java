package com.example.application.views;

import com.example.application.DynamicSurvey;
import com.example.application.Questions.AskMoreIfYesQuestion;
import com.example.application.Questions.QuestionUI;
import com.example.application.Questions.RollQuestion;
import com.example.application.Questions.ComboBoxQuestion;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

class DynamicMorningSurvey extends DynamicSurvey {
    DynamicMorningSurvey() {
        super(new QuestionUI[] {
            new AskMoreIfYesQuestion( 
                "Tager du nogen gange sovemedicin eller melatonin piller", 
                new QuestionUI[] {
                    new ComboBoxQuestion("Hvad tager du?", "Sovemedicin", "Melatonin")
                }
            ),
            new ComboBoxQuestion(
                "Hvad foretog du dig de sidste par timer inden du gik i seng?", "Ting", "Sager"),
            new RollQuestion(
                "I går gik jeg i seng klokken:"),
            new RollQuestion(
                "Jeg slukkede lyset klokken:"),
            new RollQuestion(
                "Efter jeg slukkede lyset, sov jeg ca. efter:"),
            new ComboBoxQuestion(
                "Jeg vågnede cirka x gange i løbet af natten:", "1", "2", "3"),
            new RollQuestion(
                "Jeg var sammenlagt vågen i cirka x minutter i løbet af natten"),
            new RollQuestion(
                "I morges vågnede jeg klokken:"),
            new RollQuestion(
                "Og jeg stod op klokken:")
            


        });
    }
    /*Question[] surveyQuestions = {};*/
}

class DynamicEveningSurvey extends DynamicSurvey {

    DynamicEveningSurvey() {
        super(new QuestionUI[] {
            new AskMoreIfYesQuestion(
                "Har du været fysisk aktiv i dag?",
                new QuestionUI[] {
                    new RollQuestion("Hvor mange minutter?"),
                    new RollQuestion("Hvornår på dagen?")
                }
            ),
            new AskMoreIfYesQuestion(
                "Har du været ude i dagslys?",
                new QuestionUI[] {
                    new RollQuestion("Hvornår på dagen?")
                }
            ),
            new AskMoreIfYesQuestion(
                 "Har du drukket koffeinholdige drikke i dag?", 
                new QuestionUI[] {
                    new ComboBoxQuestion ("Hvilke drikke (flere kan vælges)", "Monster", "Kaffe", "Sodavand"),
                    new RollQuestion("Hvornår på dagen indtager du den sidste drik?")
                }
            ),
            new AskMoreIfYesQuestion("Har du drukket alkohol?",
                new QuestionUI[] {
                    new RollQuestion ("Hvornår på dagen drak du den sidste genstand?"),
                    new ComboBoxQuestion("Hvor mange genstande har du ca. drukket i løbet af dagen?", "1", "2", "3")
                }
            ),
            new AskMoreIfYesQuestion("Har du sovet i løbet af dagen?",
                new QuestionUI[]{
                    new RollQuestion("Hvornår på dagen")
                }
            )

            

        
        });
    }
}

@Route("survey-oop")
public class SurveyOOPView extends VerticalLayout {

    private int currentIndex = 0;

    // Layout that will contain the current question UI
    public VerticalLayout content;

    private DynamicMorningSurvey survey;
    private QuestionUI[] allQ;

    public SurveyOOPView() {

        // 1) Choose which survey to show
        this.survey = new DynamicMorningSurvey();   // or new DynamicEveningSurvey()
        this.allQ   = survey.surveyQuestions;

        Button next = new Button("Næste >");
        Button prev = new Button("< Tilbage");
        H3 h3 = new H3("Spørgeskema");

        // 2) Overall layout setup for the page
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.START);
        setJustifyContentMode(JustifyContentMode.BETWEEN);

        // 3) Layout that holds the *current* question
        content = new VerticalLayout();
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
        prev.getStyle().set("background-color", "#262ecaff").set("color", "white");
        next.getStyle().set("background-color", "#262ecaff").set("color", "white");
        prev.getStyle().set("font-size", "30px");
        next.getStyle().set("font-size", "30px");

        // 6) Add heading + content + buttons to the page
        add(h3, content, buttons);

        // 7) Show the first question
        showQuestion(currentIndex);

        // 8) Next / Previous button logic
        next.addClickListener(e -> {
            if (currentIndex < allQ.length - 1) {   // only if there IS a next question
                currentIndex++;
                survey.nextQuestion();              // keep internal index in sync (optional)
                showQuestion(currentIndex);
            }
        });

        prev.addClickListener(e -> {
            if (currentIndex > 0) {                 // only if there IS a previous question
                currentIndex--;
                survey.previousQuestion();          // keep internal index in sync (optional)
                showQuestion(currentIndex);
            }
        });
    }

    private void showQuestion(int index) {
        if (index < 0 || index >= allQ.length) {
            return; // safety
        }

        content.removeAll();                        // remove old question UI
        Component qComponent = allQ[index].drawUI(); // build new question UI
        content.add(qComponent);                    // show it
        content.setAlignSelf(FlexComponent.Alignment.START, qComponent);
    }
}
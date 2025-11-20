package com.example.application.views;


import com.example.application.Questions.AskMoreIfYesQuestion;
import com.example.application.Questions.QuestionUI;
import com.example.application.Questions.RollQuestion;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("survey-oop")
public class SurveyOOPView extends VerticalLayout {

    QuestionUI[] morningSurveyQuestions = new QuestionUI[] {
        new AskMoreIfYesQuestion(
            "Har du været fysisk aktiv i dag?",
            new QuestionUI[] {
                new AskMoreIfYesQuestion(
                "Har du været fysisk aktiv i dag?",
                    new QuestionUI[] {
                        new RollQuestion("Hvor mange minutter?"),
                        new RollQuestion("Hvornår på dagen?")
                    }
                )

            }
        ),

        /*  new ComboBoxAnswer("How's your daddy", "Good", "Bad"),
        new ComboBoxAnswer("What is up", "Not much", "A lot"),*/
        new RollQuestion("When is up"),
        new AskMoreIfYesQuestion("Do you drink?", new QuestionUI[] {})
    };

    private int currentIndex = 0;

    // Layout that will contain the current question UI
    public VerticalLayout content;

    public SurveyOOPView() {
        Button next = new Button("Næste >");
        Button prev = new Button("< Tilbage");
        H3 h3 = new H3();


        // Overall layout setup for the page
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.BETWEEN);

        // This layout will hold the current question's UI
        content = new VerticalLayout();
        content.setAlignItems(FlexComponent.Alignment.START);
        content.setWidth("60%");
        content.setPadding(true);

        // All questions in the survey
        QuestionUI[] allQ = morningSurveyQuestions;

        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout(prev, next);
        buttons.setWidthFull();
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Component qNum = allQ[currentIndex].drawUI();

        layout.add(qNum);
        layout.setAlignSelf(Alignment.START, h3);
        layout.setAlignSelf(Alignment.START, qNum);
        add(layout, buttons);

        next.setWidth("220px");
        next.setHeight("110px");
        prev.setWidth("220px");
        prev.setHeight("110px");
        prev.getStyle().set("background-color", "#262ecaff").set("color", "white");
        next.getStyle().set("background-color", "#262ecaff").set("color", "white");
        prev.getStyle().set("font-size", "30px");
        next.getStyle().set("font-size", "30px");
        
        

        // Add main content + buttons to this view
        add(content, buttons);

        // Show the first question initially
        /*showQuestion(currentIndex);*/
        
        
        
        // Add each question's UI to the page
        /*for (Question a : allQ) {
            add(a.drawUI());
        }*/
    }

}

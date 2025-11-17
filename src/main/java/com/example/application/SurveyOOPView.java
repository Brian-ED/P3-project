package com.example.application;


import com.example.application.AllQ;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.Route;

@Route("survey-oop")
public class SurveyOOPView extends VerticalLayout {

    private final Answer[] allQ;


    private int currentIndex = 0;

    // Layout that will contain the current question UI
    public VerticalLayout content;

    public SurveyOOPView() {


        final Object[] QUESTIONS = null;
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

        Answer[] pafu = {
            new RollAnswer("Hvor mange minutter?"),
            new RollAnswer("Hvornår på dagen?")
        };

        // Follow-up questions if user answers "Ja"
        AskMoreIfYes [] amiy = {new AskMoreIfYes(
         "Har du været fysisk aktiv i dag?",
            new Answer[] {
            new RollAnswer("Hvor mange minutter?"),
            new RollAnswer("Hvornår på dagen?")
            }
        )};

        // All questions in the survey
        allQ = new Answer [] {
            new AskMoreIfYes("Har du været fysisk aktiv i dag?", amiy),

           /*  new ComboBoxAnswer("How's your daddy", "Good", "Bad"),
            new ComboBoxAnswer("What is up", "Not much", "A lot"),*/
            new RollAnswer("When is up"),
            new AskMoreIfYes("Do you drink?", amiy)
        };

        
        
        
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
        /*for (Answer a : allQ) {
            add(a.drawUI());
        }*/
    }

}



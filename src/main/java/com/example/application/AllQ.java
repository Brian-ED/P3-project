package com.example.application;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AllQ {

    
    public static final String[] QUESTIONS = {

            // Evening
            "Et par timer efter jeg stod op følte jeg mig 1: Udmattet/Uoplagt - 5: Frisk/Oplagt",
            "Har du været fysisk aktiv i dag?",
            //Hvis ja
            "Hvor mange minutter?",
            "Hvornår på dagen?",
            //Næste spørgsmål
            "Har du været ude i dagslys?",
            //Hvis ja
            "Hvor mange minutter?",
            //Næste spørgsmål
            "Har du drukket koffeinholdige drikke i dag?",
            //Hvis ja
            "Hvilke drikke (flere kan vælges)",
            "Hvornår indtager du det sidste? (ruller med tidspunkt)",
            //Næste spørgsmål
            "Har du drukket alkohol?",
            //Hvis ja
            "Hvornår på dagen drak du den sidste genstand?",
            "Hvor mange genstande har du ca. drukket i løbet af dagen?",
            //Næste spørgsmål
            "Har du sovet i løbet af dagen?",

            // Morning
            "Tager du nogen gange sovemedicin eller melatonin piller",
            "Hvad foretog du dig de sidste par timer inden du gik i seng?",
            "I går gik jeg i seng klokken:",
            "Jeg slukkede lyset klokken:",
            "Efter jeg slukkede lyset, sov jeg ca. efter:",
            "Jeg vågnede cirka x gange i løbet af natten:",
            "Jeg var sammenlagt vågen i cirka x minutter i løbet af natten",
            "I morges vågnede jeg klokken:",
            "Og jeg stod op klokken:"
    };


    
    public static VerticalLayout createQuestionLayout(int index) {
        String questionText = QUESTIONS[index];

        
        H3 heading = new H3(questionText);

        // Layout that wraps the question (and later maybe fields, radio buttons, etc.)
        VerticalLayout layout = new VerticalLayout(heading);
        layout.setAlignItems(FlexComponent.Alignment.START);
        layout.setWidth("220px");
        layout.setHeight("110px");

        return layout;
    }
}

/*
YesNoElab(x) = Yes/No with elaboration on yes
RollMenuTime = _
Enum = _
ComboBox = _

Q1Even: YesNoElab(RollMenuTime, ComboBox)
Q2Even: YesNoElab(RollMenuTime)
Q3Even: YesNoElab(Checkboxes, RollMenuTime) 
 */

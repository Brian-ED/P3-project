package com.example.application;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

abstract class ok {
     protected final String questionTitle;

    protected ok(String questionTitle) {
        this.questionTitle = questionTitle;
    }
    abstract Component drawUI();
}

class ComboBoxAnswer extends ok {

    private final String[] options;

    ComboBoxAnswer(String questionTitle, ok[]... pafu) {
        super(questionTitle);     // store questionTitle in the base class
        this.options = pafu;   // store dropdown options
    }

    @Override
    Component drawUI() {
        ComboBox<String> cb = new ComboBox<>(questionTitle); // label = question
        cb.setItems(options);                               // fill dropdown
        return cb;                                          // return the component
    };
}

class RollAnswer extends ok {

    RollAnswer(String questionTitle) {
        super(questionTitle);
    }

    @Override
    Component drawUI() {
        // A simple TimePicker with the question as its label
        return new TimePicker(questionTitle);
    }
}

class AskMoreIfYes extends ok {

    private final ok[] extraQuestionsInYes;

    AskMoreIfYes(String questionTitle, ok[] extraQuestionsInYes) {
        super(questionTitle);
        this.extraQuestionsInYes = extraQuestionsInYes;
    }

    @Override
    Component drawUI() {
        // Container for the yes/no question + any follow-up questions
        VerticalLayout container = new VerticalLayout();

        // Yes/No selector
        RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
        yesNo.setLabel(questionTitle);      // show the main question
        yesNo.setItems("Ja", "Nej");

        container.add(yesNo);

        // When user changes the answer...
        yesNo.addValueChangeListener(e -> {
            // First remove all follow-up questions (keep only the yes/no)
            container.removeAll();
            container.add(yesNo);

            // If they answered "Ja", add the extra questions underneath
            if ("Ja".equals(e.getValue())) {
                for (ok extra : extraQuestionsInYes) {
                    container.add(extra.drawUI());
                }
            }
        });

        return container;
    }
}

class MainProgram {
  static void main(String[] args) {
    ok[] amiy = {
      new ComboBoxAnswer("Did you drink vodka?"),
    };
    ok[] allQ = {
      new ComboBoxAnswer( "Et par timer efter jeg stod op følte jeg mig 1: Udmattet/Uoplagt - 5: Frisk/Oplagt"),
      new ComboBoxAnswer("what is up"),
      new RollAnswer("when is up"),
      new AskMoreIfYes("Do you drink", amiy),
    };
    allQ[0].drawUI();
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


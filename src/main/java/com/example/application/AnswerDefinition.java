package com.example.application;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.timepicker.TimePicker;

interface QuestionUI {
    abstract Component drawUI();
}

class ComboBoxQuestion extends Question implements QuestionUI {

    private final String[] options;

    ComboBoxQuestion(String questionTitle, String... options) {
        super(questionTitle);     // store questionTitle in the base class
        this.options = options;   // store dropdown options
    }

    @Override
    public Component drawUI() {
        ComboBox<String> cb = new ComboBox<>(questionTitle); // label = question
        cb.setItems(options);                               // fill dropdown
        return cb;                                          // return the component
    }
}

class RollQuestion extends Question implements QuestionUI {

    RollQuestion(String questionTitle) {
        super(questionTitle);
    }

    @Override
    public Component drawUI() {
        // A simple TimePicker with the question as its label
        return new TimePicker(questionTitle);
    }
}

class AskMoreIfYesQuestion extends Question implements QuestionUI {

    private final QuestionUI[] extraQuestionsInYes;

    AskMoreIfYesQuestion(String questionTitle, QuestionUI[] extraQuestionsInYes) {
        super(questionTitle);
        this.extraQuestionsInYes = extraQuestionsInYes;
    }

    @Override
    public Component drawUI() {
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
                for (QuestionUI extra : extraQuestionsInYes) {
                    container.add(extra.drawUI());
                }
            }
        });

        return container;
    }
}

class MainProgram {
  static void main(String[] args) {
    QuestionUI[] amiy = {
      new ComboBoxQuestion("Did you drink vodka?"),
    };
    QuestionUI[] allQ = {
      new ComboBoxQuestion( "Et par timer efter jeg stod op følte jeg mig 1: Udmattet/Uoplagt - 5: Frisk/Oplagt"),
      new ComboBoxQuestion("what is up"),
      new RollQuestion("when is up"),
      new AskMoreIfYesQuestion("Do you drink", amiy),
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

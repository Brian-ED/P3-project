package com.example.application;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.timepicker.TimePicker;

public class Questions {
    static public abstract class QuestionUI extends Question {
        QuestionUI(String question) {
            super(question);
        }
        abstract public Component drawUI();
    }

    static public class ComboBoxQuestion extends QuestionUI {

        private final String[] options;

        public ComboBoxQuestion(String questionTitle, String... options) {
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

    static public class RollQuestion extends QuestionUI {

        public RollQuestion(String questionTitle) {
            super(questionTitle);
        }

        @Override
        public Component drawUI() {
            // A simple TimePicker with the question as its label
            return new TimePicker(questionTitle);
        }
    }

    static public class AskMoreIfYesQuestion extends QuestionUI {

        private final QuestionUI[] extraQuestionsInYes;

        public AskMoreIfYesQuestion(String questionTitle, QuestionUI[] extraQuestionsInYes) {
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
}

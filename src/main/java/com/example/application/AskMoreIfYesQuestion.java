package com.example.application;

import com.example.model.Question;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;

public class AskMoreIfYesQuestion {

    private Component drawYesNoElaborate(Question question) {
        // Container for the yes/no question + any follow-up questions
        VerticalLayout container = new VerticalLayout();

        // Yes/No selector
        RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
        yesNo.setLabel(question.questionTitle);      // show the main question
        yesNo.setItems("Ja", "Nej");

        container.add(yesNo);

        // When user changes the answer...
        yesNo.addValueChangeListener(e -> {
            // First remove all follow-up questions (keep only the yes/no)
            container.removeAll();
            container.add(yesNo);

            // If they answered "Ja", add the extra questions underneath
            if ("Ja".equals(e.getValue())) {
                for (Question extra : question.subQuestions) {
                    container.add(drawUI(extra));
                }
            }
        });

        return container;
    }

    private Component drawComboBox(Question question) {
            ComboBox<String> cb = new ComboBox<>(question.questionTitle); // label = question
            cb.setItems(question.answerCases.orElse(new String[0]));      // fill dropdown
            return cb;
    }

    private Component drawTimeField(Question question) {
        return new TimePicker(question.questionTitle);
    }

    private Component drawCheckboxGroup(Question question) {
        CheckboxGroup<String> x = new CheckboxGroup<>(question.questionTitle); // TODO Add listener as second argument here
        for (String s : question.answerCases.orElse(new String[0])) {
            x.add(s);
        }
        return x;
    }
    private Component drawNumberField(Question question) {
        return new NumberField(question.questionTitle);
    }
    private Component drawTextField(Question question) {
        return new TextField(question.questionTitle);
    }
    private Component drawYesNo(Question question) {
        return new Checkbox(question.questionTitle);
    }

    public Component drawUI(Question question) {
        return switch (question.type) {
            case CHECKBOX_GROUP   -> drawCheckboxGroup (question);
            case COMBOBOX         -> drawComboBox      (question);
            case NUMBER_FIELD     -> drawNumberField   (question);
            case TEXT_FIELD       -> drawTextField     (question);
            case TIME_FIELD       -> drawTimeField     (question);
            case YES_NO           -> drawYesNo         (question);
            case YES_NO_ELABORATE -> drawYesNoElaborate(question);
        };
    }
}

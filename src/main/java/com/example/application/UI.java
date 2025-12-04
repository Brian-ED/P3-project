package com.example.application;

import com.example.application.model.Question;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;

public class UI {

    private static Component drawComboBox(Question question) {
        ComboBox<String> cb = new ComboBox<>(question.questionTitle); // label = question
        cb.setItems(question.answerCases.orElse(new String[0]));      // fill dropdown
        return cb;
    }

    private static Component drawTimeField(Question question) {
        return new TimePicker(question.questionTitle);
    }

    private static Component drawCheckboxGroup(Question question) {
        CheckboxGroup<String> x = new CheckboxGroup<>(question.questionTitle); // TODO Add listener as second argument here
        for (String s : question.answerCases.orElse(new String[0])) {
            x.add(s);
        }
        return x;
    }
    private static Component drawNumberField(Question question) {
        return new NumberField(question.questionTitle);
    }
    private static Component drawTextField(Question question) {
        return new TextField(question.questionTitle);
    }
    private static Component drawYesNo(Question question) {

        // Title
        H3 h3 = new H3(question.questionTitle);

        // Yes/No selector
        RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
        yesNo.setItems("Ja", "Nej");

        // Make each question be on their own row
        yesNo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        // Container for the yes/no question + any follow-up questions
        VerticalLayout container = new VerticalLayout(h3, yesNo);
        container.setAlignItems(FlexComponent.Alignment.START);
        container.setJustifyContentMode(JustifyContentMode.START);
        container.setAlignSelf(Alignment.START, h3);
        container.setAlignSelf(Alignment.START, yesNo);

        // When user changes the answer...
        yesNo.addValueChangeListener(e -> {
            // First remove all follow-up questions (keep only the yes/no)
            container.removeAll();
            container.add(h3);
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

    public static Component drawUI(Question question) {
        return switch (question.type) {
            case CHECKBOX_GROUP   -> drawCheckboxGroup (question);
            case COMBOBOX         -> drawComboBox      (question);
            case NUMBER_FIELD     -> drawNumberField   (question);
            case TEXT_FIELD       -> drawTextField     (question);
            case TIME_FIELD       -> drawTimeField     (question);
            case YES_NO           -> drawYesNo         (question);
        };
    }
}

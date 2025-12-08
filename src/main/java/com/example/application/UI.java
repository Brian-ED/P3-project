package com.example.application;

import com.example.application.database.ClDiDB.Questions.ComboBoxQuestion;
import com.example.application.database.ClDiDB.Questions.DurationQuestion;
import com.example.application.database.ClDiDB.Questions.GenericQuestion;
import com.example.application.database.ClDiDB.Questions.RollQuestion;
import com.example.application.database.ClDiDB.Questions.TextFieldQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateComboboxQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateComboboxRollQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateRollComboboxQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateRollQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateRollRollQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoQuestion;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;

public class UI {
    private static Component drawYesNo(YesOrNoElaborateComboboxQuestion question) {

        // Title
        H3 h3 = new H3(question.getMainQuestionTitle());

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
                container.add(new ComboBox<String>(question.getComboboxQuestionTitle()) {{setItems(question.getComboboxQuestionOptions());}});
            }
        });

        return container;
    }



    private static Component drawYesNo(YesOrNoElaborateComboboxRollQuestion question) {

        // Title
        H3 h3 = new H3(question.getMainQuestionTitle());

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
                container.add(
                    new ComboBox<String>(question.getComboboxQuestionTitle()) {{setItems(question.getComboboxQuestionOptions());}},
                    new TimePicker(question.getRollQuestionTitle())
                );
            }
        });

        return container;
    }


    private static Component drawYesNo(YesOrNoQuestion question) {

        // Title
        H3 h3 = new H3(question.getMainQuestionTitle());

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

        return container;
    }


    private static Component drawYesNo(YesOrNoElaborateRollRollQuestion question) {

        // Title
        H3 h3 = new H3(question.getMainQuestionTitle());

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
                container.add(
                    new TimePicker(question.getRollQuestion0Title()),
                    new TimePicker(question.getRollQuestion1Title())
                );
            }
        });

        return container;
    }

    private static Component drawYesNo(YesOrNoElaborateRollQuestion question) {

        // Title
        H3 h3 = new H3(question.getMainQuestionTitle());

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
                container.add(new TimePicker(question.getRollQuestionTitle()));
            }
        });

        return container;
    }

    private static Component drawYesNo(YesOrNoElaborateRollComboboxQuestion question) {

        // Title
        H3 h3 = new H3(question.getMainQuestionTitle());

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
                container.add(
                    new TimePicker(question.getRollQuestionTitle()),
                    new ComboBox<String>(question.getComboboxQuestionTitle()) {{setItems(question.getComboboxQuestionOptions());}}
                );
            }
        });

        return container;
    }

    public static Component drawUI(GenericQuestion<?> question) {
        return switch (question) {
            case ComboBoxQuestion x -> new ComboBox<String>(x.getMainQuestionTitle()) {{setItems(x.getComboboxQuestionOptions());}}; // label = question
            case YesOrNoElaborateRollRollQuestion x -> drawYesNo(x);
            case YesOrNoElaborateRollQuestion x -> drawYesNo(x);
            case YesOrNoElaborateComboboxRollQuestion x -> drawYesNo(x);
            case YesOrNoElaborateRollComboboxQuestion x -> drawYesNo(x);
            case YesOrNoElaborateComboboxQuestion x -> drawYesNo(x);
            case RollQuestion x -> new TimePicker(x.getMainQuestionTitle());
            case DurationQuestion x -> null;
            case TextFieldQuestion x -> new TextField(x.getMainQuestionTitle());
            case YesOrNoQuestion x -> drawYesNo(x);
        };
    }
}

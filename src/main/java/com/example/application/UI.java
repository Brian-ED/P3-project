package com.example.application;

import com.example.application.database.ClDiDB.Questions.ComboBoxQuestion;
import com.example.application.database.ClDiDB.Questions.RollQuestionShort;
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
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class UI {

    private static final String WIDTH_COMBO = "300px";
    private static final String WIDTH_TIME  = "250px";
    private static final String WIDTH_TEXT  = "600px";
    private static final String WIDTH_DEFAULT = "300px";

    private static VerticalLayout questionBlock(String titleText, Component input) {
        return questionBlock(titleText, input, defaultWidthFor(input));
        }

        private static VerticalLayout questionBlock(String titleText, Component input, String inputWidth) {
        // Title styling (consistent everywhere)
        H3 title = new H3(titleText);
        title.getStyle()
                .set("margin", "0")
                .set("padding", "0")
                .set("font-size", "28px");

        // Prevent duplicate titles: clear label on inputs that have one
        if (input instanceof HasLabel labeled) {
            labeled.setLabel("");
        }

        // Standardize input width
        if (inputWidth != null) {
            if (input instanceof HasSize sized) {
                sized.setWidth(inputWidth);
            } else {
                input.getElement().getStyle().set("width", inputWidth);
            }
        }

        // Standard wrapper layout
        VerticalLayout wrapper = new VerticalLayout(title, input);
        wrapper.setPadding(false);
        wrapper.setSpacing(false);
        wrapper.setAlignItems(FlexComponent.Alignment.START);
        wrapper.setWidthFull();
        wrapper.getStyle().set("gap", "10px");

        return wrapper;
    }

    private static String defaultWidthFor(Component input) {
        if (input instanceof TimePicker) return WIDTH_TIME;
        if (input instanceof ComboBox) return WIDTH_COMBO;
        if (input instanceof TextArea) return WIDTH_TEXT;
        if (input instanceof TextField) return WIDTH_TEXT;
        return WIDTH_DEFAULT;
    }


    private static TimePicker rollTimePicker(String label) {
        TimePicker tp = new TimePicker(label);
        tp.setStep(java.time.Duration.ofMinutes(15)); // your new default
        tp.setAutoOpen(true); // optional but nice
        return tp;
    }

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

            // Combobox follow-up (title + input)
            Span comboTitle = new Span(question.getComboboxQuestionTitle());

            ComboBox<String> combo = new ComboBox<>();
            combo.setItems(question.getComboboxQuestionOptions());
            combo.setWidth("300px");
            combo.setPlaceholder("Vælg en mulighed");
            combo.setClearButtonVisible(true);

            // Time follow-up (title + input)
            Span timeTitle = new Span(question.getRollQuestionTitle());

            TimePicker tp = rollTimePicker("");   // IMPORTANT: no label
            tp.setWidth("250px");

            // Add them
            container.add(comboTitle, combo, timeTitle, tp);
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

            H3 h3 = new H3(question.getMainQuestionTitle());

            RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
            yesNo.setItems("Ja", "Nej");
            yesNo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

            VerticalLayout container = new VerticalLayout(h3, yesNo);
            container.setAlignItems(FlexComponent.Alignment.START);

            yesNo.addValueChangeListener(e -> {
                container.removeAll();
                container.add(h3, yesNo);

                if ("Ja".equals(e.getValue())) {
                    container.add(
                            new TimePicker(question.getRollQuestion0Title()), // RollQuestionShort renderer
                            new TimePicker(question.getRollQuestion1Title())  // RollQuestion renderer
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

                // Combobox follow-up (title + input)
                Span comboTitle = new Span(question.getComboboxQuestionTitle());

                ComboBox<String> combo = new ComboBox<>();
                combo.setItems(question.getComboboxQuestionOptions());
                combo.setWidth("300px");
                combo.setPlaceholder("Vælg en mulighed");
                combo.setClearButtonVisible(true);

                // Time follow-up (title + input)
                Span timeTitle = new Span(question.getRollQuestionTitle());

                TimePicker tp = rollTimePicker("");   // IMPORTANT: no label
                tp.setWidth("250px");

                // Add them
                container.add(comboTitle, combo, timeTitle, tp);
            }
        });

        return container;
    }

    public static Component drawUI(GenericQuestion<?> question) {
        return switch (question) {
            case ComboBoxQuestion x -> {
                ComboBox<String> cb = new ComboBox<>();
                cb.setItems(x.getComboboxQuestionOptions());
                cb.setPlaceholder("Vælg en mulighed");
                cb.setClearButtonVisible(true);

                yield questionBlock(x.getMainQuestionTitle(), cb);
            }

            case YesOrNoElaborateRollRollQuestion x -> drawYesNo(x);
            case YesOrNoElaborateRollQuestion x -> drawYesNo(x);
            case YesOrNoElaborateComboboxRollQuestion x -> drawYesNo(x);
            case YesOrNoElaborateRollComboboxQuestion x -> drawYesNo(x);
            case YesOrNoElaborateComboboxQuestion x -> drawYesNo(x);

            // Wakeup etc. = coarse (1 hour)

            // Bedtime + lights off = fine (5 minutes)
            case RollQuestion x -> {
                TimePicker tp = new TimePicker(x.getMainQuestionTitle());
                tp.setStep(Duration.ofMinutes(15));
                tp.setPlaceholder("Vælg tidspunkt");
                tp.setAutoOpen(true);            // clicking the field opens the list
                tp.setClearButtonVisible(true);
                yield tp;
            }

            // Duration questions = fine (5 minutes) AND no more null => no blank page
            case RollQuestionShort x -> {
                ComboBox<Integer> cb = new ComboBox<>(x.getMainQuestionTitle());

                List<Integer> values = new ArrayList<>();
                for (int i = 0; i <= 250; i += 5) {
                    values.add(i);
                }

                cb.setItems(values);
                cb.setItemLabelGenerator(i -> i + " min");
                cb.setPlaceholder("Vælg antal minutter");
                cb.setClearButtonVisible(true);

                yield cb;
            }

        case TextFieldQuestion x -> {
            TextArea ta = new TextArea(x.getMainQuestionTitle());
            ta.setWidth("600px");          // pick a size you like
            ta.setMinHeight("150px");      // "big field"
            ta.setMaxHeight("600px");      // optional
            ta.setClearButtonVisible(true);

            // This makes it expand downwards while typing:
            ta.setHeight("auto");
            ta.getStyle().set("overflow", "hidden");
            ta.getElement().executeJs("""
                const ta = this.inputElement;
                const resize = () => { ta.style.height = 'auto'; ta.style.height = ta.scrollHeight + 'px'; };
                ta.addEventListener('input', resize);
                resize();
            """);

            yield questionBlock(x.getMainQuestionTitle(), ta);
        }

        case YesOrNoQuestion x -> drawYesNo(x);
    };
}

}

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
import com.example.application.model.AnswerPayload;
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
import java.time.ZonedDateTime;
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
                .set("font-size", "22px");

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

    private static boolean safeIsAnswered(GenericQuestion<?> q) {
        try {
            // If toPayload() works, there is something stored
            q.getAnswer().toPayload();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static Component drawYesNo(YesOrNoElaborateComboboxQuestion question) {

        H3 h3 = new H3(question.getMainQuestionTitle());

        RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
        yesNo.setItems("Ja", "Nej");
        yesNo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        ComboBox<String> combo = new ComboBox<>();
        String[] opts = question.getComboboxQuestionOptions();
        combo.setItems(opts);
        combo.setWidth("300px");
        combo.setPlaceholder("Vælg en mulighed");
        combo.setClearButtonVisible(true);

        VerticalLayout container = new VerticalLayout();
        container.setAlignItems(FlexComponent.Alignment.START);

        Runnable render = () -> {
            container.removeAll();
            container.add(h3, yesNo);

            if ("Ja".equals(yesNo.getValue())) {
                container.add(new Span(question.getComboboxQuestionTitle()), combo);
            }
        };

        Runnable saveIfComplete = () -> {
            if (!"Ja".equals(yesNo.getValue())) return;
            if (combo.getValue() == null) return;

            short idx = -1;
            for (short i = 0; i < opts.length; i++) {
                if (opts[i].equals(combo.getValue())) { idx = i; break; }
            }
            if (idx < 0) return;

            question.getAnswer().answer(
                    new com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxPayload(true, idx)
            );
        };

        yesNo.addValueChangeListener(e -> {
            String v = e.getValue();
            if (v == null) return;

            if ("Nej".equals(v)) {
                question.getAnswer().answer(
                        new com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxPayload(false, (short) -1)
                );
            }

            render.run();
            saveIfComplete.run();
        });

        combo.addValueChangeListener(e -> saveIfComplete.run());

        // RESTORE
        try {
            var p = question.getAnswer().toPayload();
            yesNo.setValue(p.yesNo() ? "Ja" : "Nej");

            if (p.yesNo() && p.whichIsSelected() != null && p.whichIsSelected() >= 0 && p.whichIsSelected() < opts.length) {
                combo.setValue(opts[p.whichIsSelected()]);
            }
        } catch (Exception ignored) {}

        render.run();
        return container;
    }

    private static Component drawYesNo(YesOrNoElaborateComboboxRollQuestion question) {

        H3 h3 = new H3(question.getMainQuestionTitle());

        RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
        yesNo.setItems("Ja", "Nej");
        yesNo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        // Follow-up components (create ONCE so values can be restored)
        ComboBox<String> combo = new ComboBox<>();
        String[] opts = question.getComboboxQuestionOptions();
        combo.setItems(opts);
        combo.setWidth("300px");
        combo.setPlaceholder("Vælg en mulighed");
        combo.setClearButtonVisible(true);

        TimePicker tp = rollTimePicker("");
        tp.setWidth("250px");
        tp.setPlaceholder("Vælg tidspunkt");
        tp.setClearButtonVisible(true);

        VerticalLayout container = new VerticalLayout();
        container.setAlignItems(FlexComponent.Alignment.START);

        Runnable render = () -> {
            container.removeAll();
            container.add(h3, yesNo);

            if ("Ja".equals(yesNo.getValue())) {
                container.add(
                    new Span(question.getComboboxQuestionTitle()),
                    combo,
                    new Span(question.getRollQuestionTitle()),
                    tp
                );
            }
        };

        // Helper: save only when we have enough info
        Runnable saveIfComplete = () -> {
            if (!"Ja".equals(yesNo.getValue())) return;
            if (combo.getValue() == null) return;
            if (tp.getValue() == null) return;

            short idx = -1;
            for (short i = 0; i < opts.length; i++) {
                if (opts[i].equals(combo.getValue())) { idx = i; break; }
            }
            if (idx < 0) return;

            var zdt = java.time.ZonedDateTime.now()
                    .withHour(tp.getValue().getHour())
                    .withMinute(tp.getValue().getMinute())
                    .withSecond(0)
                    .withNano(0);

            question.getAnswer().answer(
                    new com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxRollPayload(true, idx, zdt)
            );
        };

        // LISTENERS
        yesNo.addValueChangeListener(e -> {
            String v = e.getValue();
            if (v == null) return;

            if ("Nej".equals(v)) {
                // Persist "Nej" so it restores later
                question.getAnswer().answer(
                        new com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxRollPayload(
                                false,
                                (short) -1,
                                java.time.ZonedDateTime.now()
                        )
                );
            }

            render.run();
            saveIfComplete.run(); // if they picked "Ja" and followups already filled
        });

        combo.addValueChangeListener(e -> saveIfComplete.run());
        tp.addValueChangeListener(e -> saveIfComplete.run());

        // RESTORE (if previously answered)
        try {
            var p = question.getAnswer().toPayload();
            yesNo.setValue(p.yesNo() ? "Ja" : "Nej");

            if (p.yesNo()) {
                if (p.whichIsSelected() != null && p.whichIsSelected() >= 0 && p.whichIsSelected() < opts.length) {
                    combo.setValue(opts[p.whichIsSelected()]);
                }
                if (p.timestamp() != null) {
                    tp.setValue(p.timestamp().toLocalTime());
                }
            }
        } catch (Exception ignored) {
            // unanswered → leave empty
        }

        render.run();
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

    private static Component drawYesNo(YesOrNoElaborateRollComboboxQuestion question) {

        H3 h3 = new H3(question.getMainQuestionTitle());

        RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
        yesNo.setItems("Ja", "Nej");
        yesNo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        // Follow-ups (create ONCE)
        TimePicker tp = new TimePicker();
        tp.setStep(Duration.ofMinutes(15));
        tp.setPlaceholder("Vælg tidspunkt");
        tp.setAutoOpen(true);
        tp.setClearButtonVisible(true);

        ComboBox<String> cb = new ComboBox<>();
        String[] opts = question.getComboboxQuestionOptions();
        cb.setItems(opts);
        cb.setPlaceholder("Vælg en mulighed");
        cb.setClearButtonVisible(true);

        VerticalLayout container = new VerticalLayout();
        container.setAlignItems(FlexComponent.Alignment.START);

        Runnable render = () -> {
            container.removeAll();
            container.add(h3, yesNo);

            if ("Ja".equals(yesNo.getValue())) {
                container.add(
                    new Span(question.getComboboxQuestionTitle()),
                    cb,
                    new Span(question.getRollQuestionTitle()),
                    tp
                );
            }
        };

        Runnable saveIfComplete = () -> {
            if (!"Ja".equals(yesNo.getValue())) return;
            if (cb.getValue() == null) return;
            if (tp.getValue() == null) return;

            short idx = -1;
            for (short i = 0; i < opts.length; i++) {
                if (opts[i].equals(cb.getValue())) { idx = i; break; }
            }
            if (idx < 0) return;

            ZonedDateTime ts = ZonedDateTime.now()
                    .withHour(tp.getValue().getHour())
                    .withMinute(tp.getValue().getMinute())
                    .withSecond(0).withNano(0);

            question.getAnswer().answer(new AnswerPayload.YesOrNoElaborateRollComboboxPayload(true, ts, idx));
        };

        yesNo.addValueChangeListener(e -> {
            String v = e.getValue();
            if (v == null) return;

            if ("Nej".equals(v)) {
                // Must fill NOT NULL fields
                ZonedDateTime now = ZonedDateTime.now();
                question.getAnswer().answer(new AnswerPayload.YesOrNoElaborateRollComboboxPayload(false, now, (short) -1));
            }

            render.run();
            saveIfComplete.run();
        });

        cb.addValueChangeListener(e -> saveIfComplete.run());
        tp.addValueChangeListener(e -> saveIfComplete.run());

        // RESTORE
        try {
            var p = question.getAnswer().toPayload();
            yesNo.setValue(p.yesNo() ? "Ja" : "Nej");

            if (p.yesNo()) {
                if (p.whichIsSelected() != null && p.whichIsSelected() >= 0 && p.whichIsSelected() < opts.length) {
                    cb.setValue(opts[p.whichIsSelected()]);
                }
                if (p.timestamp() != null) {
                    tp.setValue(p.timestamp().toLocalTime());
                }
            }
        } catch (Exception ignored) { }

        render.run();
        return container;
    }

    private static Component drawYesNoRollRoll(YesOrNoElaborateRollRollQuestion question) {

        H3 h3 = new H3(question.getMainQuestionTitle());

        RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
        yesNo.setItems("Ja", "Nej");
        yesNo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        // Follow-ups (create ONCE so they can be restored)
        ComboBox<Integer> minutesCb = new ComboBox<>();
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i <= 250; i += 5) values.add(i);
        minutesCb.setItems(values);
        minutesCb.setItemLabelGenerator(i -> i + " min");
        minutesCb.setPlaceholder("Vælg antal minutter");
        minutesCb.setClearButtonVisible(true);

        TimePicker tp = new TimePicker();
        tp.setStep(Duration.ofMinutes(15));
        tp.setPlaceholder("Vælg tidspunkt");
        tp.setAutoOpen(true);
        tp.setClearButtonVisible(true);

        VerticalLayout container = new VerticalLayout();
        container.setAlignItems(FlexComponent.Alignment.START);

        Runnable render = () -> {
            container.removeAll();
            container.add(h3, yesNo);

            if ("Ja".equals(yesNo.getValue())) {
                container.add(
                    new Span(question.getRollQuestion0Title()),
                    minutesCb,
                    new Span(question.getRollQuestion1Title()),
                    tp
                );
            }
        };

        Runnable saveIfComplete = () -> {
            if (!"Ja".equals(yesNo.getValue())) return;
            if (minutesCb.getValue() == null) return;
            if (tp.getValue() == null) return;

            int mins = minutesCb.getValue();

            // Encode minutes as a ZonedDateTime: today's midnight + minutes
            ZonedDateTime ts1 = ZonedDateTime.now()
                    .withHour(0).withMinute(0).withSecond(0).withNano(0)
                    .plusMinutes(mins);

            // Encode time of day as ZonedDateTime: today at chosen time
            ZonedDateTime ts2 = ZonedDateTime.now()
                    .withHour(tp.getValue().getHour())
                    .withMinute(tp.getValue().getMinute())
                    .withSecond(0).withNano(0);

            question.getAnswer().answer(new AnswerPayload.YesOrNoElaborateRollRollPayload(true, ts1, ts2));
        };

        yesNo.addValueChangeListener(e -> {
            String v = e.getValue();
            if (v == null) return;

            if ("Nej".equals(v)) {
                // Must fill NOT NULL fields
                ZonedDateTime now = ZonedDateTime.now();
                question.getAnswer().answer(new AnswerPayload.YesOrNoElaborateRollRollPayload(false, now, now));
            }

            render.run();
            saveIfComplete.run();
        });

        minutesCb.addValueChangeListener(e -> saveIfComplete.run());
        tp.addValueChangeListener(e -> saveIfComplete.run());

        // RESTORE
        try {
            var p = question.getAnswer().toPayload();
            yesNo.setValue(p.yesNo() ? "Ja" : "Nej");

            if (p.yesNo()) {
                // Decode minutes from timestamp1 (minutes since midnight)
                int mins = p.timestamp1().toLocalTime().toSecondOfDay() / 60;
                if (values.contains(mins)) minutesCb.setValue(mins);

                tp.setValue(p.timestamp2().toLocalTime());
            }
        } catch (Exception ignored) { }

        render.run();
        return container;
    }

    private static Component drawYesNoRoll(YesOrNoElaborateRollQuestion question) {

        H3 h3 = new H3(question.getMainQuestionTitle());

        RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
        yesNo.setItems("Ja", "Nej");
        yesNo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        TimePicker tp = rollTimePicker("");
        tp.setWidth("250px");
        tp.setPlaceholder("Vælg tidspunkt");
        tp.setClearButtonVisible(true);

        VerticalLayout container = new VerticalLayout();
        container.setAlignItems(FlexComponent.Alignment.START);

        Runnable render = () -> {
            container.removeAll();
            container.add(h3, yesNo);

            if ("Ja".equals(yesNo.getValue())) {
                container.add(new Span(question.getRollQuestionTitle()), tp);
            }
        };

        Runnable saveIfComplete = () -> {
            if (!"Ja".equals(yesNo.getValue())) return;
            if (tp.getValue() == null) return;

            var zdt = java.time.ZonedDateTime.now()
                    .withHour(tp.getValue().getHour())
                    .withMinute(tp.getValue().getMinute())
                    .withSecond(0)
                    .withNano(0);

            question.getAnswer().answer(
                    new com.example.application.model.AnswerPayload.YesOrNoElaborateRollPayload(true, zdt)
            );
        };

        yesNo.addValueChangeListener(e -> {
            String v = e.getValue();
            if (v == null) return;

            if ("Nej".equals(v)) {
                question.getAnswer().answer(
                        new com.example.application.model.AnswerPayload.YesOrNoElaborateRollPayload(false, java.time.ZonedDateTime.now())
                );
            }

            render.run();
            saveIfComplete.run();
        });

        tp.addValueChangeListener(e -> saveIfComplete.run());

        // RESTORE
        try {
            var p = question.getAnswer().toPayload();
            yesNo.setValue(p.yesNo() ? "Ja" : "Nej");
            if (p.yesNo() && p.timestamp() != null) {
                tp.setValue(p.timestamp().toLocalTime());
            }
        } catch (Exception ignored) {}

        render.run();
        return container;
    }

    private static Component rollQuestionShortUI(String title) {
        ComboBox<Integer> cb = new ComboBox<>(title);

        List<Integer> values = new ArrayList<>();
        for (int i = 0; i <= 250; i += 5) {
            values.add(i);
        }

        cb.setItems(values);
        cb.setItemLabelGenerator(i -> i + " min");
        cb.setPlaceholder("Vælg antal minutter");
        cb.setClearButtonVisible(true);

        return cb;
    }

    private static Component rollQuestionUI(String title) {
        TimePicker tp = new TimePicker(title);
        tp.setStep(Duration.ofMinutes(15));
        tp.setPlaceholder("Vælg tidspunkt");
        tp.setAutoOpen(true);
        tp.setClearButtonVisible(true);
        return tp;
    }

    public static Component drawUI(GenericQuestion<?> question, boolean hasAnswered) {
        return switch (question) {
            case ComboBoxQuestion x -> {
                ComboBox<String> cb = new ComboBox<>();
                String[] opts = x.getComboboxQuestionOptions();
                cb.setItems(opts);
                cb.setPlaceholder("Vælg en mulighed");
                cb.setClearButtonVisible(true);

                // RESTORE
                if (hasAnswered) {
                    try {
                        short idx = x.getAnswer().toPayload().whichIsSelected();
                        if (idx >= 0 && idx < opts.length) cb.setValue(opts[idx]);
                    } catch (Exception ignored) {}
                }

                // SAVE
                cb.addValueChangeListener(e -> {
                    String v = e.getValue();
                    if (v == null) return;
                    for (short i = 0; i < opts.length; i++) {
                        if (opts[i].equals(v)) {
                            x.getAnswer().answer(new AnswerPayload.ComboBoxPayload(i));
                            break;
                        }
                    }
                });

                yield questionBlock(x.getMainQuestionTitle(), cb);
            }

            case RollQuestion x -> {
                TimePicker tp = new TimePicker(x.getMainQuestionTitle());
                tp.setStep(Duration.ofMinutes(15));
                tp.setPlaceholder("Vælg tidspunkt");
                tp.setAutoOpen(true);
                tp.setClearButtonVisible(true);

                // RESTORE
                if (hasAnswered) {
                    try {
                        var ts = x.getAnswer().toPayload().timestamp();
                        tp.setValue(ts.toLocalTime());
                    } catch (Exception ignored) {}
                }

                // SAVE
                tp.addValueChangeListener(e -> {
                    var t = e.getValue();
                    if (t == null) return;
                    var zdt = java.time.ZonedDateTime.now()
                            .withHour(t.getHour())
                            .withMinute(t.getMinute())
                            .withSecond(0)
                            .withNano(0);
                    x.getAnswer().answer(new AnswerPayload.RollPayload(zdt));
                });

                // keep your consistent title styling:
                yield questionBlock(x.getMainQuestionTitle(), tp);
            }

            case RollQuestionShort x -> {
                ComboBox<Integer> cb = new ComboBox<>(x.getMainQuestionTitle());

                List<Integer> values = new ArrayList<>();
                for (int i = 0; i <= 250; i += 5) values.add(i);

                cb.setItems(values);
                cb.setItemLabelGenerator(i -> i + " min");
                cb.setPlaceholder("Vælg antal minutter");
                cb.setClearButtonVisible(true);

                // RESTORE
                if (hasAnswered) {
                    try {
                        cb.setValue(x.getAnswer().toPayload().minutes());
                    } catch (Exception ignored) {}
                }

                // SAVE
                cb.addValueChangeListener(e -> {
                    Integer v = e.getValue();
                    if (v == null) return;
                    x.getAnswer().answer(new AnswerPayload.DurationPayload(v));
                });

                yield questionBlock(x.getMainQuestionTitle(), cb);
            }

            case TextFieldQuestion x -> {
                TextArea ta = new TextArea(x.getMainQuestionTitle());
                ta.setWidth("600px");
                ta.setMinHeight("150px");
                ta.setClearButtonVisible(true);

                // RESTORE
                if (hasAnswered) {
                    try {
                        ta.setValue(x.getAnswer().toPayload().text());
                    } catch (Exception ignored) {}
                }

                // SAVE (on every change)
                ta.addValueChangeListener(e -> {
                    String v = e.getValue();
                    if (v == null) v = "";
                    x.getAnswer().answer(new AnswerPayload.TextFieldPayload(v));
                });

                yield questionBlock(x.getMainQuestionTitle(), ta);
            }

            case YesOrNoQuestion x -> {
                RadioButtonGroup<String> yesNo = new RadioButtonGroup<>();
                yesNo.setItems("Ja", "Nej");
                yesNo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

                // RESTORE
                if (hasAnswered) {
                    try {
                        boolean yn = x.getAnswer().toPayload().yesNo();
                        yesNo.setValue(yn ? "Ja" : "Nej");
                    } catch (Exception ignored) {}
                }

                // SAVE
                yesNo.addValueChangeListener(e -> {
                    String v = e.getValue();
                    if (v == null) return;
                    x.getAnswer().answer(new AnswerPayload.YesOrNoPayload("Ja".equals(v)));
                });

                yield questionBlock(x.getMainQuestionTitle(), yesNo);
            }

            // keep your existing elaborate cases for now (or we can bind them next)
            case YesOrNoElaborateRollRollQuestion x -> drawYesNoRollRoll(x);
            case YesOrNoElaborateRollQuestion x -> drawYesNoRoll(x);
            case YesOrNoElaborateComboboxRollQuestion x -> drawYesNo(x);
            case YesOrNoElaborateRollComboboxQuestion x -> drawYesNo(x);
            case YesOrNoElaborateComboboxQuestion x -> drawYesNo(x);
        };
    }

}

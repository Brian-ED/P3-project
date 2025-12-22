package com.example.application.model;

import java.time.ZonedDateTime;

import com.example.application.model.AnswerPayload.ComboBoxPayload;
import com.example.application.model.AnswerPayload.DurationPayload;
import com.example.application.model.AnswerPayload.RollPayload;
import com.example.application.model.AnswerPayload.TextFieldPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxRollPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollComboboxPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollRollPayload;
import com.example.application.model.AnswerPayload.YesOrNoPayload;

public sealed interface AnswerPayload permits
    YesOrNoPayload,
    YesOrNoElaborateComboboxPayload,
    RollPayload,
    ComboBoxPayload,
    DurationPayload,
    TextFieldPayload,
    YesOrNoElaborateRollRollPayload,
    YesOrNoElaborateRollPayload,
    YesOrNoElaborateComboboxRollPayload,
    YesOrNoElaborateRollComboboxPayload {

    public record YesOrNoPayload                      (Boolean yesNo                                                    ) implements AnswerPayload {}
    public record YesOrNoElaborateComboboxPayload     (Boolean yesNo, Short whichIsSelected                             ) implements AnswerPayload {}
    public record RollPayload                         (ZonedDateTime timestamp                                          ) implements AnswerPayload {}
    public record ComboBoxPayload                     (Short whichIsSelected                                            ) implements AnswerPayload {}
    public record DurationPayload                     (Integer minutes                                                  ) implements AnswerPayload {}
    public record TextFieldPayload                    (String text                                                      ) implements AnswerPayload {}
    public record YesOrNoElaborateRollRollPayload     (Boolean yesNo, ZonedDateTime timestamp1, ZonedDateTime timestamp2) implements AnswerPayload {}
    public record YesOrNoElaborateRollPayload         (Boolean yesNo, ZonedDateTime timestamp                           ) implements AnswerPayload {}
    public record YesOrNoElaborateComboboxRollPayload (Boolean yesNo, Short whichIsSelected, ZonedDateTime timestamp    ) implements AnswerPayload {}
    public record YesOrNoElaborateRollComboboxPayload (Boolean yesNo, ZonedDateTime timestamp, Short whichIsSelected    ) implements AnswerPayload {}

}

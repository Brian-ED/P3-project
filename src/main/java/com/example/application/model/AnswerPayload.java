package com.example.application.model;

import com.example.application.model.AnswerPayloads.ComboBoxPayload;
import com.example.application.model.AnswerPayloads.RollPayload;
import com.example.application.model.AnswerPayloads.YesOrNoElaborateIntPayload;
import com.example.application.model.AnswerPayloads.YesOrNoPayload;

public sealed interface AnswerPayload permits
    YesOrNoPayload,
    YesOrNoElaborateIntPayload,
    RollPayload,
    ComboBoxPayload {}

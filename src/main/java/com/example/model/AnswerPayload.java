package com.example.model;

import com.example.model.AnswerPayloads.ComboBoxPayload;
import com.example.model.AnswerPayloads.RollPayload;
import com.example.model.AnswerPayloads.YesOrNoPayload;

public sealed interface AnswerPayload permits
    YesOrNoPayload,
    RollPayload,
    ComboBoxPayload {}

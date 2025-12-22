package com.example.application.model;

import java.util.Map;

import com.example.application.database.ClDiDB.SurveyEveningRow;
import com.example.application.database.ClDiDB.SurveyMorningRow;

public class SurveySizes {
    public static final Map<SurveyType, Integer> SURVEY_SIZES = Map.of(
        SurveyType.morning, SurveyMorningRow.size(),
        SurveyType.evening, SurveyEveningRow.size()
    );
}

package com.example.application;

import java.util.Optional;

abstract class User {
    String fullName;
    public abstract void loadMainPage();

    /* TODO */ public void login() {}
    /* TODO */ public void logout() {}
    /* TODO */ public void seeAnsweredSurvey() {}
}
class Citizen extends User {
    DynamicSurvey currentSurvey;
    SleepAdvisor assignedAdvisor;
    AnsweredSurvey[] answeredSurveys;

    /* TODO */ public void answerMorningSurvey() {}
    /* TODO */ public void answerEveningSurvey() {}
    /* TODO */ public void loadMainPage() {}
}
class SleepAdvisor extends User {
    /* TODO */ public void searchCitizensList() {}
    /* TODO */ public void showOnlyYourCitizens() {}
    /* TODO */ public void sortCitizensByAdvisor() {}
    /* TODO */ public void sortCitizensByName() {}
    /* TODO */ public void changeAssignedAdvisor() {}
    /* TODO */ public void seeCitizenData() {}
    /* TODO */ public void loadMainPage() {}
}

class DynamicSurvey {
    DynamicQuestion[] questions;
    Integer currentQuestion;
    Question[] surveyQuestions;

    DynamicSurvey() {
        this.currentQuestion = 0;

        this.questions = new DynamicQuestion[surveyQuestions.length];
        for (int i=0; i<questions.length; i++){
            this.questions[i] = new DynamicQuestion(surveyQuestions[i]);
        };
    }
    public void nextQuestion() {
        if (currentQuestion + 1 < questions.length) {
            currentQuestion += 1;
        }
    }
    public void previousQuestion() {
        if (currentQuestion > 0) {
            currentQuestion -= 1;
        }
    }
    public Optional<AnsweredSurvey> submitAnswers() {
        Answer[] answers = new Answer[questions.length];

        // Fill in answers array from dynamic questions
        for (int i=0; i<questions.length; i++) {
            DynamicQuestion q = questions[i];
            if (q.question.answerToQuestion.isEmpty()) {
                return Optional.empty();
            } else {
                answers[i] = q.question.answerToQuestion.orElseThrow();
            }
        }

        return Optional.of(new AnsweredSurvey(answers, SurveyType.morning));
    }
}

class DynamicQuestion {
    Question question;

    DynamicQuestion(Question question) {
        this.question = question;
    }
    public void answerQuestion(Answer answer) {
        this.question.answerQuestion(answer);
    }
}
class AnsweredSurvey {
    SurveyType surveyType;
    Answer[] answers;

    AnsweredSurvey(Answer[] answers, SurveyType surveyType) {
        this.surveyType = surveyType;
        this.answers = answers;
    }
}
enum SurveyType {
    morning,
    evening,
}

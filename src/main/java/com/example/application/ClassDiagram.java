package com.example.application;

import java.util.Optional;

abstract class User {
    String fullName;
    /* TODO */ public void seeAnsweredSurvey() {}
}
class Citizen extends User {
    DynamicSurvey currentSurvey;
    SleepAdvisor assignedAdvisor;
    AnsweredSurvey[] answeredSurveys;

    /* TODO */ public void answerMorningSurvey() {}
    /* TODO */ public void answerEveningSurvey() {}
    public void changeAssignedAdvisor(SleepAdvisor newSleepAdvisor) {
        this.assignedAdvisor = newSleepAdvisor;
    }
}
class SleepAdvisor extends User {
    /* TODO */ public void searchCitizensList() {}
    /* TODO */ public void showOnlyYourCitizens() {}
    /* TODO */ public void sortCitizensByAdvisor() {}
    /* TODO */ public void sortCitizensByName() {}
    public void changeAssignedAdvisor(Citizen citizen) {
        citizen.changeAssignedAdvisor(this);
    }
    /* TODO */ public void seeCitizenData() {}
}

class DynamicSurvey {
    DynamicQuestion[] questions;
    Integer currentQuestion;
    SurveyType surveyType;

    DynamicSurvey(Object[] questionsList) {
        this.currentQuestion = 0;

        this.questions = new DynamicQuestion[questionsList.length];
        for (int i=0; i<questions.length; i++){
            this.questions[i] = new DynamicQuestion(questionsList[i]);
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
            if (q.answer.isEmpty()) {
                return Optional.empty();
            }
            answers[i] = q.answer.orElseThrow();
        }

        return Optional.of(new AnsweredSurvey(answers, surveyType));
    }
}
class DynamicQuestion {
    Optional<Answer> answer;
    Object question;

    DynamicQuestion(Object question) {
        this.question = question;
    }
    public void answerQuestion(Answer answer) {
        this.answer = Optional.of(answer);
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
abstract class Answer {}

class Question {
    protected final String questionTitle;
    Optional<Answer> answerToQuestion;

    protected Question(String questionTitle) {
        this.questionTitle = questionTitle;
    }
    void answerQuestion(Answer answer) {
        this.answerToQuestion = Optional.of(answer);
    }
}

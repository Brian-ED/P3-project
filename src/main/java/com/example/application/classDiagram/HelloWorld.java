class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}

abstract class User {
    String fullName;
    public abstract void loadMainPage();

    /* TODO */ public void login() {}
    /* TODO */ public void logOut() {}
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
    SurveyType surveyType;

    DynamicSurvey(DynamicQuestion[] questions, SurveyType surveyType) {
        this.currentQuestion = 0;
        this.questions = questions;
        this.surveyType = surveyType;
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
    public AnsweredSurvey submitAnswers() {
        Answer[] answers = new Answer[questions.length];

        // Fill in answers array from dynamic questions
        for(int i=0; i<questions.length; i++){
            DynamicQuestion q = questions[i];
            answers[i] = q.getAnswer();
        }

        return new AnsweredSurvey(answers, surveyType);
    }
}
class DynamicQuestion {
    Answer answer;

    /* TODO */ public void answerQuestion() {}

    public Answer getAnswer() {
        return answer;
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
/* TODO */ enum SurveyType {}

class Answer {}

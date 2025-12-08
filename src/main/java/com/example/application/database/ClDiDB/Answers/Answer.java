package com.example.application.database.ClDiDB.Answers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.example.application.model.AnswerPayload;

public sealed abstract class Answer<P extends AnswerPayload>
    permits YesOrNoElaborateRollRollAnswer,
            YesOrNoElaborateRollAnswer,
            YesOrNoElaborateComboboxRollAnswer,
            YesOrNoElaborateRollComboboxAnswer,
            YesOrNoElaborateComboboxAnswer,
            ComboBoxAnswer,
            RollAnswer,
            DurationAnswer,
            TextFieldAnswer,
            YesOrNoAnswer
    {

    private final Class<P> payloadClass;

    protected Answer(Class<P> payloadClass) {
        this.payloadClass = payloadClass;
    }

    protected abstract void answerProto(P payload);

    public final class Token {}

    private final Map<Token, Consumer<Answer<P>>> listeners = new HashMap<>();

    public Token addListener(Consumer<Answer<P>> listener) {
        Token t = new Token();
        listeners.put(t, listener);
        return t;
    }

    public void removeListener(Token token) {
        listeners.remove(token);
    }

    public void answer(P payload) {
        answerProto(payload);
        for (var listener : listeners.values()) {
            listener.accept(this);
        }
    };

    public abstract P toPayload();

    public Class<P> getPayloadClass() {
        return payloadClass;
    }

}

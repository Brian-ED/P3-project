package com;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class Button {
    public static class Token {}

    private final Map<Token, Function<Integer,Void>> listeners = new HashMap<>();

    public Token addListener(Function<Integer,Void> listener) {
        Token t = new Token();
        listeners.put(t, listener);
        return t;
    }

    public void removeListener(Token token) {
        listeners.remove(token);
    }

    public void press(Integer value) {
        for (var listener : listeners.values()) {
            listener.apply(value);
        }
    }
}

public class observerExample {

    private static Void page5(Integer whichQuestion) {
        if (whichQuestion == 5) {
            System.out.println("This is Page 5!!");
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println("\n\n\nStarted");

        Button nextButton = new Button();

        nextButton.addListener(x -> page5(x));

        nextButton.press(0);
        nextButton.press(1);
        nextButton.press(2);
        nextButton.press(6);
    }
}

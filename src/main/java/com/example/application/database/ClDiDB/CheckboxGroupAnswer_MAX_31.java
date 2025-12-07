package com.example.application.database.ClDiDB;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CheckboxGroupAnswer_MAX_31 {

    private static Integer signedIntSizeExcludingSignBit = 31;

    @Column(nullable = false)
    private Integer answer;

    private static Integer booleansToInt(boolean[] checkboxes) {
        assert checkboxes.length <= signedIntSizeExcludingSignBit;
        Integer result = 0;
        for (Integer i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i]) {
                result |= 1 << i;
            }
        }
        return result;
    }

    private static boolean[] intToBooleans(Integer value) {
        boolean[] arr = new boolean[31];
        for (int i = 0; i < 31; i++) {
            arr[i] = (value & (1 << i)) != 0;
        }
        return arr;
    }
    CheckboxGroupAnswer_MAX_31(boolean[] answer) {
        super();
        assert answer.length <= signedIntSizeExcludingSignBit;

        this.answer = booleansToInt(answer);
    }

    public boolean[] getAnswer() {
    return intToBooleans(answer);
    }
};

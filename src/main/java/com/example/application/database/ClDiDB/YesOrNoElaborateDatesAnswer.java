package com.example.application.database.ClDiDB;

import java.time.LocalTime;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
abstract public class YesOrNoElaborateDatesAnswer {

    @Column(nullable = false)
    private boolean yesNoValue;

    @Column
    @Nullable
    private LocalTime elabValueDate1;

    @Column
    @Nullable
    private LocalTime elabValueDate2;

    protected YesOrNoElaborateDatesAnswer(boolean yesNoValue, LocalTime elabValueDate1, LocalTime elabValueDate2) {
        if (yesNoValue == false) {
            assert elabValueDate1 == null;
            assert elabValueDate2 == null;
        } else {
            assert elabValueDate1 != null;
            assert elabValueDate2 != null;
        }
        this.yesNoValue = yesNoValue;
        this.elabValueDate1 = elabValueDate1;
        this.elabValueDate2 = elabValueDate2;
    }

}

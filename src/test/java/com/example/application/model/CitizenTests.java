package com.example.application.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.application.model.Citizen.CitizenListenerToken;

public class CitizenTests {

    private static int check1 = 0;
    private static int check2 = 0;

    @Test
    public void test1() {
        Citizen x = new Citizen(null, null, null, null);
        check1 = 0;
        check2 = 0;
        CitizenListenerToken t1 = x.addUpdateListener(() -> {check1 = 1;});
        CitizenListenerToken t2 = x.addUpdateListener(() -> {check2 = 1;});
        assertEquals(0, check1);
        assertEquals(0, check2);
        x.clearAssignedAdvisor();
        assertEquals(1, check1);
        assertEquals(1, check2);
        x.removeUpdateListener(t2);
        check1 = 0;
        check2 = 0;
        x.clearAssignedAdvisor();
        assertEquals(1, check1);
        assertEquals(0, check2);
        x.removeUpdateListener(t1);
    }
}

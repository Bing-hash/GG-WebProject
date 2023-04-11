package com.ggweb.api.model;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class DonorTest {
    
    @Test
    void testContructor() {
        Donor actual = new Donor("Test Testerson", 100.00);

        assertTrue(actual.getClass() == Donor.class);
    }

    @Test
    void testName() {
        String expected = "Test Testerson";

        Donor don = new Donor("Test Testerson", 100.00);
        String actual = don.getName();

        assertTrue(actual == expected);
    }

    @Test
    void testAmmount() {
        double expected = 100.00;

        Donor don = new Donor("Test Testerson", 100.00);
        double actual = don.getAmmount();

        assertTrue(actual == expected);
    }

    
}

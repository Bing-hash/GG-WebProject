package com.ggweb.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class RunnerTest {
    
    @Test
    void testContructor() {
        Runner actual = new Runner(1, "Usain Bolt");

        assertTrue(actual.getClass() == Runner.class);
    }

    @Test
    void testID() {
        int expected = 1;

        Runner run = new Runner(1, "Usain Bolt");
        int actual = run.getId();

        assertTrue(expected == actual);        
    }

    @Test
    void testName() {
        String expected = "Usain Bolt";

        Runner run = new Runner(1, "Usain Bolt");
        String actual = run.getName();

        assertTrue(expected == actual);        
    }

    @Test
    void testGetDonors() {
        ArrayList<Donor> expected = new ArrayList<>();

        Runner run = new Runner(1, "Usain Bolt");

        assertEquals(expected, run.getDonors());
    }

    @Test
    void testGoal() {
        double expected = 500.00;

        Runner run = new Runner(1, "Usain Bolt");
        double actual = run.getGoal();

        assertTrue(expected == actual);        
    }

    @Test
    void testTotal() {
        double expected = 100.00;

        Runner run = new Runner(1, "Usain Bolt");
        run.addDonor(new Donor("Test Testerson", 100.00));

        assertTrue(expected == run.getTotal());
    }

    @Test
    void testDonorByName() {
        Donor expected = new Donor("Test Testerson", 100.00);

        Runner run = new Runner(1, "Usain Bolt");
        run.addDonor(new Donor("Test Testerson", 100.00));
        Donor actual = run.getDonorByName("Test Testerson");

        assertEquals(expected, actual);
    }

    @Test
    void testAddDonors() {
        ArrayList<Donor> expected = new ArrayList<>();
        expected.add(new Donor("Test Testerson", 100.00));

        Runner run = new Runner(1, "Usain Bolt");
        run.addDonor(new Donor("Test Testerson", 100.00));

        assertEquals(expected, run.getDonors());
    }

    @Test
    void testRemoveDonors() {
        ArrayList<Donor> expected = new ArrayList<>();

        Runner run = new Runner(1, "Usain Bolt");
        run.addDonor(new Donor("Test Testerson", 100.00));
        run.removeDonor("Test Testerson");

        assertEquals(expected, run.getDonors());
    }
}

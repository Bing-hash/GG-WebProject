package com.ggweb.api.persistance;

import java.io.IOException;
import java.util.ArrayList;

import com.ggweb.api.model.Donor;
import com.ggweb.api.model.Runner;

/**
 * 
 * 
 * @author Nathan Dallmann
 */

public interface RunnerDAO {
    

    Runner[] getRunners() throws IOException;

    Runner getRunnerByID(int id) throws IOException;

    Runner getRunnerByName(String name) throws IOException;

    Runner createRunner(String name) throws IOException;

    boolean removeRunner(int id) throws IOException;

    ArrayList<Donor> getDonors(int runID) throws IOException;

    Donor getDonorByName(int runID, String donName) throws IOException;

    Runner addDonor(int runID, String donorName, double donorAmmount) throws IOException;

    Runner removeDonor(int runID, String name) throws IOException;
}

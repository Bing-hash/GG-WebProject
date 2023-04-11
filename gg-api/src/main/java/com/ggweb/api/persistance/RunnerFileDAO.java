package com.ggweb.api.persistance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggweb.api.model.Donor;
import com.ggweb.api.model.Runner;

/**
 * Represents a Runner entity
 * 
 * @author Nathan Dallmann
 */

 
@Component
public class RunnerFileDAO implements RunnerDAO{
    // Link between runner id and their object
    Map<Integer,Runner> runners;
    // Convert between runner and file
    private ObjectMapper objectMapper;
    // Next id for new runner
    private static int nextID;
    // File to read/write from/to
    private String filename;
    
    
   

    public RunnerFileDAO(@Value("${runners.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        // Load runners from the file
        load();
    }

    // Makes next id for new runner
    private synchronized static int nextID() {
        int id = nextID;
        nextID ++;
        return id;
    }

    // Makes an array of Runners from the tree map
    private Runner[] getRunnersArray() {
        return getRunnersArray(null);
    }

    // Makes an array of Runners from the tree map that contains a specific
    // test. If null, then no filter
    private Runner[] getRunnersArray(String containsText) {
        ArrayList<Runner> runnerArrayList = new ArrayList<>();
        for(Runner run : runners.values()) {
            if(containsText == null || run.getName().contains(containsText)) {
                runnerArrayList.add(run);
            }
        }
        Runner[] runnerArray = new Runner[runnerArrayList.size()];
        runnerArrayList.toArray(runnerArray);
        return runnerArray;
    }

    // Saves Runners from the map into the file as an array of JSON Objects
    private boolean save() throws IOException {
        Runner[] runnerArray = getRunnersArray();
        objectMapper.writeValue(new File(filename), runnerArray);
        return true;
    }

    // Loads Runners from JSON file to the tree map
    private boolean load() throws IOException{
        runners = new TreeMap<>();
        nextID = 0;
        // Turns JSON objects into an array of runners. Will throw IOexception if there is a file issue
        Runner[] runnerArray = objectMapper.readValue(new File(filename), Runner[].class);
        // Add each runner to the tree map and keep track of the greatest ID
        for(Runner run : runnerArray) {
            runners.put(run.getId(), run);
            if (run.getId() > nextID) nextID = run.getId();
        }
        // Set next ID to one greater than the max.
        nextID ++;
        return true;
    }

    @Override
    public Runner[] getRunners() throws IOException {
        synchronized(runners) {
            return getRunnersArray();
        }
    }

    @Override
    public Runner getRunnerByID(int id) throws IOException {
        synchronized(runners) {
            if (runners.containsKey(id)) {
                return runners.get(id);
            } else return null;
        }
    }

    @Override
    public Runner getRunnerByName(String name) throws IOException {
        synchronized(runners) {
            if(getRunnersArray(name).length>0) {
                return getRunnersArray(name)[0];
            } else return null;
        }
    }

    @Override
    public Runner createRunner(String name) throws IOException {
        synchronized(runners) {
                Runner run = new Runner(nextID(), name);
                runners.put(run.getId(), run);
                save();
                return run;
            
        }
    }

    @Override
    public boolean removeRunner(int id) throws IOException {
        synchronized(runners) {
            if(runners.containsKey(id)) {
                runners.remove(id);
                return save();
            } else throw new IOException("Runner does not exist: ");
        }
    }

    @Override
    public ArrayList<Donor> getDonors(int runID) throws IOException {
        synchronized(runners) {
            if(runners.containsKey(runID)) {
                Runner run = runners.get(runID);
                return run.getDonors();
            } else throw new IOException("Runner does not exist: ");
        }
    }

    @Override
    public Donor getDonorByName(int runID, String donName) throws IOException {
        synchronized(runners) {
            if(runners.containsKey(runID)) {
                Runner run = runners.get(runID);
                if(run.getDonorByName(donName) == null) {
                    return null;
                } else return run.getDonorByName(donName); 
            } return null;
        }
    }

    @Override
    public Runner addDonor(int runID, String donorName, double donorAmmount) throws IOException {
        synchronized(runners) {
            if(runners.containsKey(runID)) {
                runners.get(runID).addDonor(new Donor(donorName, donorAmmount));
                save();
                return runners.get(runID);
            } else throw new IOException("Runner does not exist: ");
        }
    }

    @Override
    public Runner removeDonor(int runID, String name) throws IOException {
        synchronized(runners) {
            if(runners.containsKey(runID)) {
                Runner run = runners.get(runID);
                if(run.getDonorByName(name) == null) {
                    throw new IOException("Runner "+run.getName()+"does not have this donor");
                } else {
                    runners.get(runID).removeDonor(name);
                    return runners.get(runID);
                }
            } else throw new IOException("Runner does not exist: ");
        }
    }



}

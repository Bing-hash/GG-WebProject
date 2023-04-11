package com.ggweb.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ggweb.api.model.Donor;
import com.ggweb.api.model.Runner;
import com.ggweb.api.persistance.RunnerDAO;

/**
 * Represents a Runner controller
 * 
 * @author Nathan Dallmann
 */

@RestController
@RequestMapping("/runners")
public class RunnerController {
    private static final Logger LOG = Logger.getLogger(RunnerController.class.getName());
    private RunnerDAO runnerDAO;

    public RunnerController(RunnerDAO runnerDAO) {
        this.runnerDAO = runnerDAO;
    }

    // Default mapping of /runners
    // Gets all runners
    // If there are no runners, returns HTTP not found message
    // If the runner DAO thows an exception, returns HTTP bad request message 
    @GetMapping("")
    public ResponseEntity<Runner[]> getRunners() {
        LOG.info("GET /runners");
        try {
            Runner[] runnerList = runnerDAO.getRunners();
            return new ResponseEntity<>(runnerList, HttpStatus.OK);
        } catch(Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Default mapping of /runners/{id}
    // Gets an runner given an id
    // If the runner with the given id does not exist, returns HTTP not found message
    // If the runner DAO thows an exception, returns HTTP bad request message
    @GetMapping("/{id}")
    public ResponseEntity<Runner> getRunner(@PathVariable int id) {
        LOG.info("GET /runner/" + id);
        try {
            Runner runner = runnerDAO.getRunnerByID(id);
            if(runner != null) {
                return new ResponseEntity<Runner>(runner, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    
    // Creates a new runner given name
    // Returns new user object
    // If the runner DAO thows an exception, returns HTTP bad request message
    @PostMapping("")
    public ResponseEntity<Runner> createRunner(@RequestBody String name) {
        LOG.info("POST /runners " + name);
        try {
            Runner checkRunner = runnerDAO.getRunnerByName(name);
            if(checkRunner == null) {
                Runner newRunner = runnerDAO.createRunner(name);
                return new ResponseEntity<>(newRunner, HttpStatus.CREATED);
            } else return new ResponseEntity<>(HttpStatus.CONFLICT);
            
        } catch(Exception e) {
            LOG.log(Level.CONFIG, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Deletes runner given an id
    // If runner does not exist, returns HTTP not found message
    // If the runner DAO thows an exception, returns HTTP bad request message
    @DeleteMapping("/{id}")
    public ResponseEntity<Runner> removeRunner(@PathVariable int id) {
        LOG.info("DELETE /runners/" + id);
        try {
            if(runnerDAO.getRunnerByID(id) != null) {
                runnerDAO.removeRunner(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            LOG.log(Level.CONFIG, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/donors")
    public ResponseEntity<ArrayList<Donor>> getDonorsFromRunnerID(@PathVariable int id) {
        LOG.info("GET /" + id + "/donors");
        try {
            ArrayList<Donor> donList = runnerDAO.getDonors(id);
            if(donList != null) {
                return new ResponseEntity<ArrayList<Donor>>(donList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            LOG.log(Level.CONFIG, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @GetMapping("/{id}/{donName}")
    // public ResponseEntity<Donor> getDonorByName(@PathVariable int id, @PathVariable String donName) {
    //     LOG.info("GET /" + id + "/donors/" + donName);
    //     try {
    //         Donor don = runnerDAO.getDonorByName(id, donName);
    //         if(donName != null) {
    //             return new ResponseEntity<>(don, HttpStatus.OK);
    //         } else {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //     } catch(Exception e) {
    //         LOG.log(Level.CONFIG, e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     }
    // }

    @PutMapping("/{id}/{donorName}/{donorAmount}")
    public ResponseEntity<Runner> addDonor(@PathVariable int id, @PathVariable String donorName, @PathVariable double donorAmount) {
        LOG.info("PUT /runners " + id + " " + donorName + " " + donorAmount);
        try {
            Runner checkRunner = runnerDAO.getRunnerByID(id);
            if(checkRunner != null) {
                Runner updatedRunner = runnerDAO.addDonor(id, donorName, donorAmount);
                if(updatedRunner != null) {
                    return new ResponseEntity<>(updatedRunner, HttpStatus.CREATED);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            LOG.log(Level.CONFIG, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/rem")
    public ResponseEntity<Runner> remDonor(@PathVariable int id, @PathVariable String donorName) {
        LOG.info("PUT /runners " + id + " " + donorName);
        try {
            Runner checkRunner = runnerDAO.getRunnerByID(id);
            if(checkRunner != null) {
                Runner updatedRunner = runnerDAO.removeDonor(id, donorName);
                if(updatedRunner != null) {
                    return new ResponseEntity<>(updatedRunner, HttpStatus.CREATED);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            LOG.log(Level.CONFIG, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

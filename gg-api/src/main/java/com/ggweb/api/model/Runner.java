package com.ggweb.api.model;

import java.util.ArrayList;
import java.util.logging.Logger;


import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents a Runner entity
 * 
 * @author Nathan Dallmann
 */

public class Runner {
    private static final Logger LOG = Logger.getLogger(Donor.class.getName());

    static final String STRING_FORMAT = "Donor [id=%d, name=%s, amount=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("donors") private ArrayList<Donor> donors;
    @JsonProperty("goal") private double goal;
    @JsonProperty("total") private double total;

    /**
     * Create a Runner with the given id, name, donors, goal, and total
     * @param id The id of the donor
     * @param name The name of the donor
     * @param donors The list of donors to a runner. Default is empty
     * @param goal The goal ammount the runner wants to reach. Default is 500.00
     * @param total The total ammount the runner has raised so far. Default is 0.00
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */

    public Runner(@JsonProperty("id") int id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
        this.donors = new ArrayList<Donor>();
        this.goal = 500.00;
        this.total = 0.00;
    }

    private void updateTotal() {
        if(donors.size() == 0) {
            this.total = 0;
        } else {
            for(Donor don: donors) {
                if(don != null) {
                    this.total += don.getAmmount();
                }
            }
        }

    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Donor> getDonors() {
        return this.donors;
    }

    public double getGoal() {
        return this.goal;
    }
    
    public double getTotal() {
        return total;
    }

    public Donor getDonorByName(String name) {
        for(int i = 0; i < this.donors.size(); i++) {
            if(this.donors.get(i).getName() == name) {
                return this.donors.get(i);
            }
        }
        return null;
    }

    public void addDonor(Donor newDonor) {
        this.donors.add(newDonor);
        updateTotal();
    }

    public Donor removeDonor(String name) {
        Donor don = getDonorByName(name);
        donors.remove(don);
        updateTotal();
        return don;
    }
}

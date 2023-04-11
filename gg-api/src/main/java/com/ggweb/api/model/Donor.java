package com.ggweb.api.model;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents a Donor entity
 * 
 * @author Nathan Dallmann
 */

public class Donor {
    private static final Logger LOG = Logger.getLogger(Donor.class.getName());

    
    // @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("amount") private double ammount;

    /**
     * Create a donor with the given id, name, and ammount
     * @param id The id of the donor
     * @param name The name of the donor
     * @param ammount The ammount of money donated by the donor
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */

    public Donor(@JsonProperty("name") String name, @JsonProperty("ammount") double ammount) {
        // this.id = id;
        this.name = name;
        this.ammount = ammount;
    }

    // public int getId() {
    //     return id;
    // }

    public String getName() {
        return name;
    }

    public double getAmmount() {
        return ammount;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Donor) {
            Donor don = (Donor) obj;
            return this.name == don.getName() && this.ammount == don.getAmmount();
        } else return false;
    }


}

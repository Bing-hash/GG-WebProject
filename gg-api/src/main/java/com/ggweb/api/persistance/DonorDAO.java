package com.ggweb.api.persistance;

import java.io.IOException;

import com.ggweb.api.model.Donor;

public interface DonorDAO {
    
    Donor getDonor(int id) throws IOException;

    Donor createDonor(int id, String name, double ammount) throws IOException;
}

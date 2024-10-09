package com.example.assesment.service.impl;

import com.example.assesment.Repoistory.SimDetailRepoistory;
import com.example.assesment.model.FileHeader;
import com.example.assesment.model.Sim;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SIMDataProcessor implements ItemProcessor<Sim, Sim> {

    @Autowired
    SimDetailRepoistory simDetailRepoistory;
    private FileHeader header;
    public SIMDataProcessor() {
        //TODO add file header validation
    }

    @Override
    public Sim process(Sim sim) throws Exception {

        String imsi = sim.getIMSI();
        Optional<Sim> dbEntity = simDetailRepoistory.findById(imsi);
        if (dbEntity.isPresent()) {
            throw new ValidationException("IMSI value already exists in Database");
        }
        return sim;
    }
}

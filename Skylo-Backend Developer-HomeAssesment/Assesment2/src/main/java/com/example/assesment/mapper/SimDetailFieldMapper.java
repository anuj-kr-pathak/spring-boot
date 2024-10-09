package com.example.assesment.mapper;

import com.example.assesment.model.Sim;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class SimDetailFieldMapper implements FieldSetMapper<Sim> {
    @Override
    public Sim mapFieldSet(FieldSet fieldSet) throws BindException {
        return new Sim(
                fieldSet.readString("IMSI"),
                fieldSet.readString("PIN1"),
                fieldSet.readString("PUK1"),
                fieldSet.readString("PIN2"),
                fieldSet.readString("PUK2"),
                fieldSet.readString("AAM1"),
                fieldSet.readString("Ki_UMTS_enc"),
                fieldSet.readString("ACC"));
    }
}

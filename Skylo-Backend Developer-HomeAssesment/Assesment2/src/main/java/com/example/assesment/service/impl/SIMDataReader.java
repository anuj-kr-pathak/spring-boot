package com.example.assesment.service.impl;

import com.example.assesment.model.FileHeader;
import com.example.assesment.model.Sim;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SIMDataReader implements ItemReader<Sim>, StepExecutionListener {
    private BufferedReader reader;
    private boolean headerProcessed = false;
    private FileHeader header;
    String filePath = "";

    @Override
    public void beforeStep(StepExecution stepExecution) {
        JobParameters jobParameters = stepExecution.getJobParameters();
        filePath = jobParameters.getString("filePath");
        //filePath = "src/main/resources/inputs/sim_data1.txt";
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        headerProcessed=false;
        return ExitStatus.COMPLETED;
    }

    public SIMDataReader() throws IOException {
    }

    @Override
    public Sim read() throws Exception {
        if (!headerProcessed) {
            // Process the header section
            processHeader();
            headerProcessed = true;
        }

        // Now process the data rows
        String line = reader.readLine();
        if (line == null) {
            return null; // End of file
        }

        // Skip non-data lines
        if (line.startsWith("*") || line.trim().isEmpty()) {
            return read(); // Recursively call until we hit a data line
        }
        return mapLineToData(line);
    }


    private void processHeader() throws IOException {
        String line;
        header = new FileHeader();

        while ((line = reader.readLine()) != null) {
            if (line.contains("Quantity")) {
                header.setQuantity(Integer.parseInt(line.split(":")[1].trim()));
            } else if (line.contains("Order_ID")) {
                header.setOrderId(line.split(":")[1].trim());
            } else if (line.contains("IMSI")) {
                header.setImsi(line.split(":")[1].trim());
            }
            if (line.contains("var out")) {
                break; // End of header, start of data
            }
        }
    }

    private Sim mapLineToData(String line) {
        // Split the line into fields by commas
        String[] fields = line.split(",");

        Sim sim = new Sim();

        sim.setIMSI(fields[1]);
        sim.setPIN1(fields[2]);
        sim.setPUK1(fields[3]);
        sim.setPIN2(fields[4]);
        sim.setPUK2(fields[5]);
        sim.setAAM1(fields[6]);
        sim.setKiUMTSEnc(fields[7]);
        sim.setAcc(fields[8]);

        return sim;
    }
}

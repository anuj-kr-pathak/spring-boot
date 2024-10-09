package com.example.assesment.service.impl;

import com.example.assesment.Repoistory.SimDetailRepoistory;
import com.example.assesment.service.FileScannerService;
import com.example.assesment.service.SimDetailService;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimServiceImpl implements SimDetailService {

    @Value("${input.file.path}")
    private String inputFolderPath;
    @Value("${output.file.path}")
    private String outputFolderPath;
    @Autowired
    FileScannerService fileService;
    @Autowired
    SimDetailRepoistory simDetailRepoistory;
    @Autowired
    public JobOperator jobOperator;
    @Autowired
    public JobExplorer jobExplorer;

    private static Map<String,Boolean> executedFileMap = new ConcurrentHashMap<>();

    public void processJob(String filePath) {
        String outputFilePath = outputFolderPath+filePath.substring(filePath.lastIndexOf('/'));
        try {
            String jobParameters = "filePath="+filePath+",timestamp=" + System.currentTimeMillis()
                    +",ouputFilePath="+ outputFilePath;
            this.jobOperator.start("simJob", jobParameters);

        }catch (Exception e){

        }
    }

    @Override
    public void processSimDetails()  {
        try {
            Set<String> latestCreatedFilesNames = fileService.scanFolderAndGetAllNonExecutedFiles(inputFolderPath,executedFileMap);
            for(String filePath : latestCreatedFilesNames){
                processJob(filePath);
            }
        }catch (Exception exception){

        }
    }

}

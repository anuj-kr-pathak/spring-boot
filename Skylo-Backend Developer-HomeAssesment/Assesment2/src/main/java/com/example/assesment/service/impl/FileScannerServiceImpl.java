package com.example.assesment.service.impl;

import com.example.assesment.service.FileScannerService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class FileScannerServiceImpl implements FileScannerService {

    @Override
    public Set<String> scanFolderAndGetAllNonExecutedFiles(String folderPath, Map<String,Boolean> executedFileMap) throws FileNotFoundException {

        File folder = new File(folderPath);
        Set<String> currentFileSet = new HashSet<>();
        if (folder.exists() && folder.isDirectory()) {
            // Populate the current set of files
            for (File file : folder.listFiles()) {
                if(!executedFileMap.containsKey(file.getName())){
                    currentFileSet.add(file.getPath());
                    executedFileMap.put(file.getName(),Boolean.TRUE);
                }
            }
        } else {
            throw new FileNotFoundException("Invalid folder path ");
        }
        return currentFileSet;
    }
}

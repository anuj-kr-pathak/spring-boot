package com.example.assesment.service;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public interface FileScannerService {

    Set<String> scanFolderAndGetAllNonExecutedFiles(String inputFolderPath, Map<String,Boolean> executedFileMap) throws FileNotFoundException;
}

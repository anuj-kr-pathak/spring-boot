package com.anuj.boot.fileprocessing.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

	@Value("${uploadDir}")
	private String uploadDir ;
	
	@RequestMapping(method=RequestMethod.POST,value = "/upload")
	public boolean upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		file.transferTo(new File(uploadDir +  file.getOriginalFilename()));
		return true;
	}
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<byte[]> download(@PathVariable("fileName") String  fileName) throws IOException{
		byte[] fileData = Files.readAllBytes(new File(uploadDir+ fileName).toPath());
		//if header is not present in response then we have bytes array only shown in response.
		//return new ResponseEntity<byte[]>(fileData,HttpStatus.OK);
		HttpHeaders headers = new  HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(fileData,headers,HttpStatus.OK);
	}
	
}

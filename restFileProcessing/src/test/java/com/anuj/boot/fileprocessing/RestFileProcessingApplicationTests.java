package com.anuj.boot.fileprocessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RestFileProcessingApplicationTests {

	private static final String HTTP_LOCALHOST_8080_DOWNLOAD = "http://localhost:8080/download/";
	private static String FILE_UPLOAD_URL = "http://localhost:8080/upload";
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${uploadDir}")
	private String uploadDir;
	
	@Test
	void testUpload() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body =  new LinkedMultiValueMap<>();
		body.add("file", new ClassPathResource("pp.jpg"));
		
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body,headers);
		
		
		ResponseEntity<Boolean> response = restTemplate.postForEntity(FILE_UPLOAD_URL, httpEntity, Boolean.class);
		System.out.println(response.getBody());
		
	}
	
	@Test
	void testDownload() throws IOException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		String responseFileName =  "pp_response.jpg";
		String requestFileName =  "pp.jpg";
		ResponseEntity<byte[]> response  =  restTemplate.exchange(HTTP_LOCALHOST_8080_DOWNLOAD+requestFileName,HttpMethod.GET, httpEntity,byte[].class);
		Files.write(Paths.get(uploadDir+responseFileName),response.getBody());
	}

}

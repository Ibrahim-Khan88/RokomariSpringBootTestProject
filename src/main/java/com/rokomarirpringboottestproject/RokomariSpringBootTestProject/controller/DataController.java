package com.rokomarirpringboottestproject.RokomariSpringBootTestProject.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rokomarirpringboottestproject.RokomariSpringBootTestProject.service.DataService;


@CrossOrigin("*")
@RestController()
@RequestMapping("/api")
public class DataController {
	
	@Autowired
	private DataService dataService;

	// http://localhost:8080/api/uploaddata
	@PostMapping("uploaddata")
	ResponseEntity<?> margeAudioANdVideo(@RequestParam("files") MultipartFile[] files) throws InterruptedException {
		String fileName = null;
		try {
			
			fileName = dataService.margeAudioAndVideo(files[0], files[1]);
			
			return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploadfile/").path(fileName).toUriString());
		} 
		catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Wrong");
		}

    }
	
}

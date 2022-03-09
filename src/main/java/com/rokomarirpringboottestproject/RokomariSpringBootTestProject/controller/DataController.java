package com.rokomarirpringboottestproject.RokomariSpringBootTestProject.controller;


import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
		
		String fileName = "", isValidate;
		
		try {
			
			isValidate = dataService.checkValidFun(files);
			
			if(isValidate.equals("notValid"))
				return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Please select a mp3 type audio and a mp4 type video file");
			
			else if(isValidate.equals("FirstAudioFile"))
				fileName = dataService.margeAudioAndVideo(files[0], files[1]);
			
			else
				fileName = dataService.margeAudioAndVideo(files[1], files[0]);
			
			return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").path(fileName).toUriString());
		} 
		catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Wrong");
		}

    }
	
}

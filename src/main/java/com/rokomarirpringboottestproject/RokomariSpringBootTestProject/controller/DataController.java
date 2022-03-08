package com.rokomarirpringboottestproject.RokomariSpringBootTestProject.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController()
@RequestMapping("/api")
public class DataController {

	// http://localhost:8080/api/data
	@PostMapping("data")
	ResponseEntity<?> margeAudioANdVideo(){

		return ResponseEntity.status(HttpStatus.CREATED).body("success");

    }
}

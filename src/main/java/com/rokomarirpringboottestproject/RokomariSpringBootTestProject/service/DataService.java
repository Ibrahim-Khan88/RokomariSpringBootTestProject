package com.rokomarirpringboottestproject.RokomariSpringBootTestProject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bytedeco.javacpp.Loader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class DataService {
	
    String dir = new ClassPathResource("static/").getFile().getAbsolutePath();
	
	public DataService() throws IOException{
		 
	}
	
	
	public String checkValidFun(MultipartFile[] files) {
		
		String file1Extension, file2Extension;
		
		if(files.length != 2) 
			return "notValid";
		
		file1Extension = files[0].getOriginalFilename().substring(files[0].getOriginalFilename().indexOf(".")+1);
		file2Extension = files[1].getOriginalFilename().substring(files[1].getOriginalFilename().indexOf(".")+1);
		
		
		if(file1Extension.equals("mp3")) {
			
			if(file2Extension.equals("mp4"))
				return "FirstAudioFile";
			else
				return "notValid";
			
		}
		else if(file1Extension.equals("mp4")){
			
			if(file2Extension.equals("mp3"))
				return "FirstVideoFile";
			else
				return "notValid";
			
		}
		else 
			return "notValid";

		
	}
	
	
	public String margeAudioAndVideo(MultipartFile audioFile, MultipartFile videoFile) throws IOException, InterruptedException {
		
		String videoFilePath, videoWithOutAudioPath, audioFilePath, finalVideoFilePath, convertMkvFormatFilePath, ffmpeg;
		dir += File.separator;
		ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
		 
		videoFilePath = dir + "inputVideo" + videoFile.getOriginalFilename().substring(videoFile.getOriginalFilename().indexOf("."), videoFile.getOriginalFilename().length());
		audioFilePath = dir + "inputAudio" + audioFile.getOriginalFilename().substring(audioFile.getOriginalFilename().indexOf("."), audioFile.getOriginalFilename().length());;
		convertMkvFormatFilePath =  dir + "convertMkvFormat.mkv";
		videoWithOutAudioPath = dir + "videoWithOutAudio.mkv";
		finalVideoFilePath = dir + "video.mkv";
		
		System.out.println(videoFilePath + " \n" + 
				convertMkvFormatFilePath +  " \n\n");
		
		// String dir = "src\\main\\resources\\static\\uploadfile"; "C:\\ffmpegPath\\ffmpeg.exe"
//		 String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);  "-y", videoFilePath.substring(videoFilePath.indexOf(".")+1, videoFilePath.length())
		Files.copy(videoFile.getInputStream(), Paths.get(videoFilePath), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(audioFile.getInputStream(), Paths.get(audioFilePath), StandardCopyOption.REPLACE_EXISTING);
		
	     ProcessBuilder process1 = new ProcessBuilder(ffmpeg, "-y", "-i", videoFilePath, convertMkvFormatFilePath);
	     process1.inheritIO().start().waitFor();
	
	     ProcessBuilder process2 = new ProcessBuilder(ffmpeg, "-y", "-i", convertMkvFormatFilePath, "-an", videoWithOutAudioPath);
	     process2.inheritIO().start().waitFor();
	     
	     ProcessBuilder process3 = new ProcessBuilder(ffmpeg,  "-y", "-i", videoWithOutAudioPath, "-i", audioFilePath, finalVideoFilePath);
	     process3.inheritIO().start().waitFor();
	     
	     return "video.mkv";

	}

	

}

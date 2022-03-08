package com.rokomarirpringboottestproject.RokomariSpringBootTestProject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bytedeco.javacpp.Loader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class DataService {
	
      String dir = new ClassPathResource("static/uploadfile/").getFile().getAbsolutePath();
	
	public DataService() throws IOException{
		 
	}
	
	
	public String margeAudioAndVideo(MultipartFile audioFile, MultipartFile videoFile) throws IOException, InterruptedException {
		
		String videoFilePath, videoWithOutAudioPath, audioFilePath, finalVideoFilePath;
		 dir += File.separator;
		 
		videoFilePath = dir + videoFile.getOriginalFilename();
		audioFilePath = dir + audioFile.getOriginalFilename();
		videoWithOutAudioPath = dir + "videoWithOutAudio." + videoFilePath.substring(videoFilePath.indexOf(".")+1, videoFilePath.length());
		finalVideoFilePath = dir + "video." + videoFilePath.substring(videoFilePath.indexOf(".")+1, videoFilePath.length());
		
		System.out.println(videoWithOutAudioPath + " \n" + 
				finalVideoFilePath +  " \n\n");
		
		// String dir = "src\\main\\resources\\static\\uploadfile";
		Files.copy(videoFile.getInputStream(), Paths.get(videoFilePath), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(audioFile.getInputStream(), Paths.get(audioFilePath), StandardCopyOption.REPLACE_EXISTING);
	
	
//		 String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
	     ProcessBuilder process1 = new ProcessBuilder("C:\\ffmpegPath\\ffmpeg.exe", "-i", videoFilePath, "-an", videoWithOutAudioPath);
	     process1.inheritIO().start().waitFor();
	     
	     ProcessBuilder process2 = new ProcessBuilder("C:\\ffmpegPath\\ffmpeg.exe", "-i", videoWithOutAudioPath, "-i", audioFilePath, finalVideoFilePath);
	     process2.inheritIO().start().waitFor();
	     
	     return "video." + videoFilePath.substring(videoFilePath.indexOf(".")+1, videoFilePath.length());

	}

	

}

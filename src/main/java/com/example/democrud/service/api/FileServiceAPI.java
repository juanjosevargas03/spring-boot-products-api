package com.example.democrud.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

import org.springframework.core.io.Resource;


public interface FileServiceAPI {

	 String save(MultipartFile file,String producto) throws Exception;
	
	 Resource load(String name) throws Exception;
	
     void copyImage(String filePath, String copyPath ) ;
    
     BufferedImage loadImage(String pathName);
    
     void saveImage(BufferedImage bufferedImage, String pathName) ;
    
     BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) ;
	
}

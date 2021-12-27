package com.example.democrud.service.impl;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.democrud.service.api.FileServiceAPI;

@Service
public class FileServiceImpl implements FileServiceAPI {

	private final Path rootFolder = Paths.get("uploads");
	private final Path tempFolder = Paths.get("temp");

	@Override
	public String save(MultipartFile file,String producto) throws Exception {
		Path pathTemp = this.tempFolder.resolve(producto+"_"+file.getOriginalFilename());
		Path path = this.rootFolder.resolve(producto+"_"+file.getOriginalFilename());
		
		if(!Files.exists(path)) {
			Files.copy(file.getInputStream(), pathTemp);
			copyImage(pathTemp.toString(), path.toString());
			Files.deleteIfExists(pathTemp);
		}
		return path.toString();
	}

	@Override
	public Resource load(String name) throws Exception {
		Path file = rootFolder.resolve(name);
		Resource resource = new UrlResource(file.toUri());
		return resource;
	}

	 //Ancho máximo
    public static int MAX_WIDTH=500;
    //Alto máximo
    public static int MAX_HEIGHT=1000;
	
    public void copyImage(String filePath, String copyPath ) {
    	   
        BufferedImage bimage = loadImage(filePath);
        if( ( bimage.getHeight()*bimage.getWidth() ) > 524288) {
            bimage = resize(bimage, MAX_WIDTH, MAX_HEIGHT);
        }
        saveImage(bimage, copyPath);
    }
     
    /*
    Este método se utiliza para cargar la imagen de disco
    */
    public BufferedImage loadImage(String pathName) {
        BufferedImage bimage = null;
        try {
            bimage = ImageIO.read(new File(pathName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimage;
    }
 
    /*
    Este método se utiliza para almacenar la imagen en disco
    */
    public void saveImage(BufferedImage bufferedImage, String pathName) {
        try {
            String format = (pathName.endsWith(".png")) ? "png" : "jpg";
            File file =new File(pathName);
            file.getParentFile().mkdirs();
            ImageIO.write(bufferedImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    /*
    Este método se utiliza para redimensionar la imagen
    */
    public BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }
}

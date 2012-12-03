package com.orcun.mezun.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int BUFFER_SIZE = 8192;

	// @Value("${fileuploadpath}")
	//private String fileUploadPath;

	public String saveFile(String fileUploadPath,UploadedFile uploadedFile)
			throws IOException {
		
		String path;
		File file;
		do{
			path = buildPath(fileUploadPath,uploadedFile.getFileName());
			file = new File(path);
		}while(file.exists());
		
		file.createNewFile();
		
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		byte[] buffer = new byte[BUFFER_SIZE];
		InputStream inputStream = uploadedFile.getInputstream();
		int bulk = inputStream.read(buffer);
		while(bulk > 0){
			fileOutputStream.write(buffer, 0, bulk);
			fileOutputStream.flush();
			bulk = inputStream.read(buffer);
		}
		fileOutputStream.close();
		inputStream.close();
		return path;
	}
	private String buildPath(String fileUploadPath,String uploadedFileName){
		long currentTimeInMilis = System.currentTimeMillis();
		String path = fileUploadPath + "/" + currentTimeInMilis;
		if(uploadedFileName.indexOf(".") != -1){
			path = path + uploadedFileName.substring(uploadedFileName.lastIndexOf("."));
		}
		return path;
	}
}

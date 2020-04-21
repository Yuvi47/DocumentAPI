package com.app.serviceInterface;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ServiceInterface {

	public String saveFile(MultipartFile file) throws Exception;

	public Resource loadFile(String docId);

	public String updateFile(String docId, MultipartFile file) throws IOException;

	public boolean deleteFile(String docId);
}

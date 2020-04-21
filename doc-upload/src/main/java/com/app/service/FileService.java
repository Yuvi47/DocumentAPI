package com.app.service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import com.app.serviceInterface.ServiceInterface;
import com.app.utils.NameGenerator;

@Service
@PropertySource("classpath:application.properties")
public class FileService implements ServiceInterface {

	@Autowired
	NameGenerator rename;

	@Autowired
	private Environment environment;

	@Override
	public String saveFile(MultipartFile file) throws Exception {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		fileName = rename.generaterFileName();

		if (!fileName.contains("..")) {

			String targetLocation = environment.getProperty("file.upload-dir");
			Path filePath = Paths.get(targetLocation).resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		} else {
			fileName = null;
		}
		return fileName;
	}

	@Override
	public Resource loadFile(String docId) {

		Resource resource = null;
		try {
			String targetLocation = environment.getProperty("file.upload-dir");
			Path filePath = Paths.get(targetLocation).resolve(docId);
			resource = new UrlResource(filePath.toUri());

			if (!resource.exists()) {
				throw new Exception();
			}
		} catch (Exception ex) {

		}

		return resource;
	}

	@Override
	public String updateFile(String docId, MultipartFile file) throws IOException {

		String fileName = docId;

		if (!fileName.contains("..")) {

			String targetLocation = environment.getProperty("file.upload-dir");
			Path filePath = Paths.get(targetLocation).resolve(fileName);

			Files.deleteIfExists(filePath);

			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		} else {
			fileName = null;
		}

		return fileName;
	}

	@Override
	public boolean deleteFile(String docId) {

		boolean response = true;
		try {
			String targetLocation = environment.getProperty("file.upload-dir");
			Path filePath = Paths.get(targetLocation).resolve(docId);

			Files.deleteIfExists(filePath);

		} catch (Exception ex) {

			response = false;
		}

		return response;
	}

}

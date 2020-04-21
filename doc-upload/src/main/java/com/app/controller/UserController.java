package com.app.controller;

import org.springframework.core.io.Resource;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.serviceInterface.ServiceInterface;

@RestController
@RequestMapping("/storage") // http://localhost:8080/storage
public class UserController {

	@Autowired
	ServiceInterface fileService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	// =====================================================================================================================//

	@PostMapping(path = "/documents", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Object> createFile(@RequestParam("file") MultipartFile file) throws Exception {

		logger.info("enter in createFile method");

		if (file.isEmpty()) {
			throw new NullPointerException("The object is null.");
		}
		String fileName = fileService.saveFile(file);
		logger.info("Out from createFile method");
		return new ResponseEntity<>(fileName, HttpStatus.CREATED);
	}

	// ========================================================================================================================//

	@GetMapping(path = "/documents/{docId}", produces = { MediaType.MULTIPART_FORM_DATA_VALUE })

	public ResponseEntity<Resource> getUser(@PathVariable @NonNull String docId) {
		logger.info("enter in getFile method");
		ResponseEntity<Resource> response = null;

		if (docId != null && !docId.isEmpty()) {
			Resource resource = fileService.loadFile(docId);

			if (resource.exists()) {
				response = ResponseEntity.ok().contentLength(docId.length()).body(resource);
			} else {
				response = ResponseEntity.notFound().build();
			}

		}
		logger.info("Out from getFile method");
		return response;
	}

	// ======================================================================================================================//

	@PutMapping(path = "/documents/{docId}", consumes = { MediaType.ALL_VALUE }, produces = { MediaType.ALL_VALUE })
	public ResponseEntity<Object> updateFile(@PathVariable String docId, @RequestParam("file") MultipartFile file)
			throws IOException {
		logger.info("enter in updateFile method");

		ResponseEntity<Object> returnResponse = null;

		String fileName = "";
		if ((docId != null && !docId.isEmpty()) && (!file.isEmpty())) {

			fileName = fileService.updateFile(docId, file);

			if (fileName != null && !fileName.isEmpty()) {

				returnResponse = new ResponseEntity<>(fileName, HttpStatus.NO_CONTENT);
			}
		} else {

			returnResponse = new ResponseEntity<>(fileName, HttpStatus.NOT_FOUND);
		}
		logger.info("enter in updateFile method");

		return returnResponse;
	}

	// ========================================================================================================================//

	@DeleteMapping(path = "/documents/{docId}", produces = { MediaType.ALL_VALUE })
	public ResponseEntity<Object> deleteFile(@PathVariable String docId) {
		logger.info("enter in deleteUser method");

		boolean response;

		ResponseEntity<Object> returnResponse = null;
		if (docId != null && !docId.isEmpty()) {

			response = fileService.deleteFile(docId);
			if (response) {
				returnResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				returnResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

		logger.info("enter in deleteUser method");

		return returnResponse;
	}

}

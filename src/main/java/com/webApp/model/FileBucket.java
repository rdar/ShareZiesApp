package com.webApp.model;

import javax.persistence.Id;

import org.springframework.web.multipart.MultipartFile;


public class FileBucket {

	MultipartFile file;
	
	@Id
	private long id;
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}

package com.webApp.model;

import java.util.ArrayList;
import java.util.List;

public class MultiFileBucket {

	List<FileBucket> files = new ArrayList<FileBucket>();
	
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MultiFileBucket(){
		files.add(new FileBucket());
		files.add(new FileBucket());
		files.add(new FileBucket());
	}
	
	public List<FileBucket> getFiles() {
		return files;
	}

	public void setFiles(List<FileBucket> files) {
		this.files = files;
	}
}


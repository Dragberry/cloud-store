package net.dragberry.cloudstore.domain;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image extends AbstractEntity {

	private static final long serialVersionUID = -8296566665076371582L;
	
	public static final String IMAGE_DIRECTORY = "y:" + File.separator + "images" + File.separator;
	
	@Column( name = "file_name" )
    private String fileName;
	
	@Column( name = "path" )
    private String path;
    
	@Column( name = "content_type" )
    private String contentType;
	
	@Column( name = "alt" )
    private String alt;
	
	
	public String getRealPath() {
		return IMAGE_DIRECTORY + path + fileName;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

}

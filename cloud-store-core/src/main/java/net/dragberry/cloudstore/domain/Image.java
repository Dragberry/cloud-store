package net.dragberry.cloudstore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image extends AbstractEntity {

	private static final long serialVersionUID = -8296566665076371582L;
	
	@Column( name = "file_name" )
    private String fileName;
    
	@Column( name = "content_type" )
    private String contentType;
	
    @Lob
    @Column( name = "content" )
    private byte[] content;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}

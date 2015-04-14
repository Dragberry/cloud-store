package net.dragberry.cloudstore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dashboard_item")
public class DashboardItem extends AbstractEntity {
	
	private static final long serialVersionUID = 6623902643407668028L;

	@Column( name = "title" )
	private String title;
	
	@Column( name = "text" )
	private String text;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}

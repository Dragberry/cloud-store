package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.Image;
import net.dragberry.cloudstore.query.ImageListQuery;
import net.dragberry.cloudstore.query.ImageQuery;

@Local
public interface ImageServiceLocal {
	
	Image saveImage(ImageQuery imageQuery);
	
	Image fetchImage(Long id);
	
	Image fetchImage(String fileName);
	
	List<Image> fetchImages(ImageListQuery imageListQuery);

}

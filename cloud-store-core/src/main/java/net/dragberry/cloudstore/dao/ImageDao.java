package net.dragberry.cloudstore.dao;

import java.util.List;

import net.dragberry.cloudstore.domain.Image;
import net.dragberry.cloudstore.query.ImageListQuery;
import net.dragberry.cloudstore.query.ImageQuery;

public interface ImageDao {
	
	Image findImage(Long id);
	
	Image findImage(String fileName);
	
	List<Image> fetchImages(ImageListQuery imageListQuery);
	
	Image saveImage(ImageQuery imageQuery);

}
